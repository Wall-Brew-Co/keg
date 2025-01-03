(defproject com.wallbrew/keg "1.2.0"
  :description "A Clojure library built to tap functions to pour runtime data"
  :url "https://github.com/Wall-Brew-Co/keg"
  :license {:name         "MIT"
            :url          "https://opensource.org/licenses/MIT"
            :distribution :repo
            :comments     "Same-as all Wall-Brew projects"}
  :scm {:name "git"
        :url  "https://github.com/Wall-Brew-Co/keg"}
  :pom-addition [:organization
                 [:name "Wall Brew Co."]
                 [:url "https://wallbrew.com"]]
  :dependencies [[org.clojure/clojure "1.12.0"]
                 [org.clojure/tools.logging "1.3.0"]
                 [metrics-clojure/metrics-clojure "2.10.0"]
                 [robert/hooke "1.3.0"]]
  :plugins [[com.github.clj-kondo/lein-clj-kondo "2024.11.14"]
            [com.wallbrew/bouncer "1.1.1"]
            [com.wallbrew/lein-sealog "1.8.0"]
            [mvxcvi/cljstyle "0.17.642"]]
  :deploy-branches ["master"]
  :deploy-repositories [["clojars" {:url           "https://clojars.org/repo"
                                    :username      :env/clojars_user
                                    :password      :env/clojars_pass
                                    :sign-releases false}]]
  :profiles {:uberjar {:aot :all}}
  :min-lein-version "2.5.3")
