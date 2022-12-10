(ns aoc-2022.days.07 
  (:require [aoc-2022.util :as util]
            [clojure.core.match :refer [match]]
            [clojure.string :refer [join]]))

(defn update-sizes [dir-sizes size path]
  (if (empty? path) 
    dir-sizes
    (recur (update dir-sizes (join "/" path) (fnil #(+ size %) 0)) size (pop path))))

;; Instead of building up a tree since we are already straversing the filesystem
;; while parsing/processing each line instead we can just build up an index of directory sizes
;; by using the path as a key and the size as a value
;; then we can just travel back up the file path each time we have a file and update the size of 
;; each one in the path
(defn process-directive [[curpath dir-sizes] directive] 
  (match directive 
    (["$" "cd" "/"]  :seq) [["/"] dir-sizes]
    (["$" "cd" ".."] :seq) [(pop curpath) dir-sizes]
    (["$" "cd" dir]  :seq) [(conj curpath dir) dir-sizes] 
    (["$" "ls"]      :seq) [curpath dir-sizes] ; no-op
    (["dir" dir]     :seq) [curpath dir-sizes] ; no-op
    ([size file]     :seq) [curpath (update-sizes dir-sizes (read-string size) curpath)] 
    :else                  [curpath dir-sizes]))

(defn analyze-file-system [lines] (->> lines (reduce process-directive [[] {"/" 0}]) last))

(defn parse [file] (->> file (util/parse-lines "07") (map (partial re-seq #"[^\s]+"))))

(defn p1 
  ([] (p1 "input"))
  ([file] (->> file
               parse
               analyze-file-system
               (filter (fn [[_ size]] (<= size 100000)))
               (map last)
               (reduce +))))


(defn find-space [hdd-size space-needed dir-sizes]
  (let [used-size (dir-sizes "/")
        free-space (- hdd-size used-size)
        space-to-find (- space-needed free-space)]
    (->> dir-sizes
         (sort-by second)
         (filter (fn [[_ size]] (>= size space-to-find)))
         first)))

(defn p2 
  ([] (p2 "input"))
  ([file] (->> file
               parse
               analyze-file-system
               (find-space 70000000 30000000))))

(str "p1: " (p1) " p2: " (p2))