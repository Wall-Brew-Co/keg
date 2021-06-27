(ns keg.impl-test
  (:require [keg.impl :as sut]
            [clojure.test :as t]))


(t/deftest name-path->qualified-name-test
  (t/is (= "clojure.core/rest" (sut/name-path->qualified-name ["clojure" "core" "rest"]))))
