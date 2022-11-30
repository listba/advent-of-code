(defproject aoc-2022 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [org.clojure/core.async "1.5.640"]
                 [org.clojure/core.match "1.0.0"]
                 [clojure-term-colors "0.1.0"]]
  :main ^:skip-aot aoc-2022.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
