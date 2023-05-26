(ns keg.impl-test
  (:require [clojure.test :as t]
            [keg.impl :as sut]))


(t/deftest name-path->qualified-name-test
  (t/is (= "clojure.core/rest" (sut/name-path->qualified-name ["clojure" "core" "rest"]))))
