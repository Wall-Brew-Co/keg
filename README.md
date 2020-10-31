# keg

A Wall Brew infrastructure library to monitor function performance in clojure applications.
wb-metrics measures the time it takes functions to execute, uses `clojure/tools.logging` to emit debug statements.

## Dependency

Add the library to your `:dependencies`:

```clojure
[com.wallbrew/keg "1.0.0"]
```

## Usage

First, require keg in your namespace:

```clojure
(:require [keg.core :as keg])
```

Then, tap the function definitions you want runtime statistics for:

```clojure
(defn my-awesome-function
  [arg1 arg2]
  (+ arg1 arg2))

(keg/tap #'my-awesome-function)
```

This creates a hook for your function.
Every time it executes, it'll log some data via `clojure/tools.logging` through whatever logging provider your project uses.
Be sure to prefix the function name with `#'` otherwise you'll encounter errors that state your functions are unnamed modules in the DynamicClassLoader.

For example:

```clojure
(my-awesome-function 3 5)
;=> 2020-10-28T17:21:25.959Z my-machine-id DEBUG [my-app.core/my-awesome-function] - {:function-name my-awesome-function, :runtime 1}
```

If you want pour extra data, you can use some of the provided formatters:

```clojure
(keg/tap #'my-awesome-function keg/pour-runtime-and-args)
(my-awesome-function 3 5)
;=> 2020-10-28T17:21:25.959Z my-machine-id DEBUG [my-app.core/my-awesome-function] - {:function-name my-awesome-function, :runtime 1, :arguments [3 5]}

(keg/tap #'my-awesome-function keg/pour-runtime-args-and-return)
(my-awesome-function 3 5)
;=> 2020-10-28T17:21:25.959Z my-machine-id DEBUG [my-app.core/my-awesome-function] - {:function-name my-awesome-function, :runtime 1, :arguments [3 5], :return-value 8}
```

Or, you can always write your own:

```clojure
(defn my-awesome-formatter
  [_function-name runtime _return & args]
  {:app-port (System/getenv "PORT")
   :runtime  runtime
   :args     args})

(keg/tap #'my-awesome-function my-awesome-formatter)
(my-awesome-function 3 5)
;=> 2020-10-28T17:21:25.959Z my-machine-id DEBUG [my-app.core/my-awesome-function] - {:app-port 8080, :runtime 1, :args [3 5]}
```

When writing your own formatters, please note the following:

- Arguments are always received in this order: `function-name runtime function-return args`
- The formatter can return text, or a map for structured logging
- Care should be used when logging arguments and/or return values in production environments
- All run times are assumed to be in milliseconds

## License

Copyright © 2019-2020 - [Wall Brew Co](https://wallbrew.com/)

This software is provided for free, public use as outlined in the [MIT License](https://github.com/Wall-Brew-Co/keg/blob/master/LICENSE)
