(defproject com.wallbrew/keg "1.1.0"
  :description "A Clojure library built to tap functions and pour data"
  :url "https://github.com/Wall-Brew-Co/keg"
  :license {:name "MIT"
            :url  "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.clojure/tools.logging "1.2.4"]
                 [metrics-clojure "2.10.0"]
                 [robert/hooke "1.3.0"]]
  :profiles {:uberjar {:aot :all}}
  :min-lein-version "2.5.3")
