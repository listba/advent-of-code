# advent-of-code-2020

Home to my advent of code solutions for 2020. All done in clojure


## Usage


    $ java -jar advent-of-code-2019-0.1.0-standalone.jar [day] [part] [& args]
    
    or if you have lein installed
    
    $ lein run [day] [part]
    
    ie
    
    $ lein run 01 1
    
    additonal inputs vary by day but typically the first optional argument is a custom input file (it looks for files in the        resources/day-[day] folder
    
    
    Additionally running with no args will run every solved day / part combination
    
    $ lein run

## License

Copyright © 2020 Ben List

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
