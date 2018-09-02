# RESTful
REST API implementation

### Spring annotation depending on version
| Http method | Spring 4.xx | Spring 5.xx |
| ----------- | ----------- | ------------|
| GET | @RequestMapping(value = "/url",  method = RequestMethod.GET) | @GetMapping("/url") |
| POST | @RequestMapping(value = "/url",  method = RequestMethod.POST) | @PostMapping("/url") |
| PUT | @RequestMapping(value = "/url",  method = RequestMethod.PUT) | @PutMapping("/url") |
| DELETE | @RequestMapping(value = "/url",  method = RequestMethod.DELETE) | @DeleteMapping("/url") |
| PATCH | @RequestMapping(value = "/url",  method = RequestMethod.PATCH) | @PatchMapping("/url") |

One more thing to memorize is if it is required to produce media type it should be done like that

| Spring 4.xx | Spring 5.xx |
| ----------- | ------------|
| @RequestMapping(value = "/url",  method = RequestMethod.GET, produces = {"application/json", "application/xml"}) | @GetMapping("/url", produces = {"application/json", "application/xml"}) |

NOTE: `@RequestMapping` annotation by default is GET method by itself.