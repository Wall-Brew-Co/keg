(ns keg.core
  "Public API for the library"
  (:require [keg.impl :as impl]
            [clojure.string :as cs]
            [robert.hooke :as hook]))

;; Formatters
;;
;; Default formatters for common use cases are delineated below
;; That said, users may supply their own formatters for logged messages.
;; If you wish to create your own formatter, it's important to know the following:
;;   - Arguments are always received in this order: function-name runtime function-return args
;;   - The formatter can return text, or a map for structured logging
;;   - Care should be used when logging arguments and/or return values in production environments
;;   - All runtimes are assumed to be in milliseconds

(defn pour-runtime
  "Returns a map to log the function name and runtime."
  [function-name runtime _ & _]
  {:function-name function-name
   :runtime       runtime})


(defn pour-runtime-and-args
  "Returns a map to log the function name, runtime, and the arguments list."
  [function-name runtime _ & args]
  {:function-name function-name
   :runtime       runtime
   :arguments     args})


(defn pour-runtime-args-and-return
  "Returns a map to log the function name, runtime, and the arguments list."
  [function-name runtime return & args]
  {:function-name function-name
   :runtime       runtime
   :arguments     args
   :return-value  return})


(defn tap
  "Adds a hook to `target-function` to collect and log execution time.
   A formatter may be additionally supplied."
  ([target-function]
   (tap target-function pour-runtime))

  ([target-function formatter]
   (let [{function-name :name namespace :ns} (meta target-function)
         qualified-name                      (conj (cs/split (str namespace) #"\.") (str function-name))
         logging-fn                          (fn [f & args] (apply impl/log-function-timing namespace formatter qualified-name f args))]
     (hook/add-hook target-function ::tap logging-fn))))
