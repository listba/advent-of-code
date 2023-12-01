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

(defn process-directive- [[curpath file-system dir-sizes] directive] 
  (match directive 
    (["$" "cd" "/"]  :seq) [["/"] file-system dir-sizes]
    (["$" "cd" ".."] :seq) [(pop curpath) file-system dir-sizes]
    (["$" "cd" dir]  :seq) [(conj curpath dir) file-system dir-sizes] 
    (["$" "ls"]      :seq) [curpath file-system dir-sizes] ; no-op
    (["dir" dir]     :seq) [curpath (assoc-in file-system (conj curpath dir) {}) dir-sizes]
    ([size file]     :seq) [curpath (assoc-in file-system (conj curpath file) (read-string size)) (update-sizes dir-sizes (read-string size) curpath)] 
    :else                  [curpath file-system dir-sizes]))

(defn analyze-file-system [lines] (->> lines (reduce process-directive- [[] {"/" {}} {"/" 0}])))

(defn parse [file] (->> file (util/parse-lines "07") (map (partial re-seq #"[^\s]+"))))

(defn compute-size [fs]
  (let [[subdirs files] (group-by (fn [[k v]] (map? v)) fs)
        local-size (->> files (map second) (apply +))
        [result subdir-size] (reduce (fn [[result subdir-size] ]))]
    
    (map compute-size subdirs)))

(comment (->> "sample" 
              parse
              analyze-file-system
              second
              (#(get % "/"))
              (group-by (fn [[k v]] (map? v)))))
(defn p1 
  ([] (p1 "input"))
  ([file] (->> file
               parse
               analyze-file-system
               (last)
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
               (last)
               (find-space 70000000 30000000)
               second)))

(str "p1: " (p1) " p2: " (p2))