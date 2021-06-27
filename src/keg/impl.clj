(ns keg.impl
  "Namespace for function instrumentation implementation."
  (:require [clojure.string :as cs]
            [clojure.tools.logging :as log]
            [metrics.meters :as meter]
            [metrics.timers :as timer])
  (:import (java.util.concurrent TimeUnit)))

(def mark-meter
  "Sends a meter metric for a given metric name."
  (comp meter/mark! meter/meter))

(defn- send-elapsed
  "Sends a timer metric for a given metric name and elapsed milliseconds."
  [metric-name millis]
  (.update (timer/timer metric-name) millis TimeUnit/MILLISECONDS))

(defn name-path->qualified-name
  [name-path]
  (format "%s/%s"
          (cs/join "." (butlast name-path))
          (last name-path)))

(defn log-function-timing
  "Logs the execution time of a given function.
   Takes a format function, a name path vector, target function, and target function arguments."
  [ns' format-fn name-path function & args]
  (let [start           (System/nanoTime)
        metric-path     #(apply vector % (rest name-path))
        function-return (try
                          (let [return (apply function args)]
                            (mark-meter (metric-path "success"))
                            return)
                          (catch Throwable t
                            (mark-meter (metric-path "failure"))
                            (throw t)))
        runtime         (int (/ (- (System/nanoTime) start) 1000000))
        function-name   (name-path->qualified-name name-path)]
    (log/log ns' :info nil (apply format-fn function-name runtime function-return args))
    (send-elapsed (metric-path "timing") runtime)
    function-return))
