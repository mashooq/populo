(ns populo.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [populo.core-test]))

(doo-tests 'populo.core-test)
