Declarative service client: [feign](https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-feign.html)
-
Feign is a declarative web service client. It makes writing web service clients easier. To use Feign create an interface and annotate it.
It has pluggable annotation support including Feign annotations and JAX-RS annotations. Feign also supports pluggable encoders and
decoders. Spring Cloud adds support for Spring MVC annotations and for using the same `HttpMessageConverters` used by 
default in Spring Web. Spring Cloud integrates Ribbon and Eureka to provide a load balanced http client when using Feign.

#### 1. How to use

To include Feign in your project use the starter with group org.springframework.cloud and artifact id spring-cloud-starter-openfeign. 
See the Spring Cloud Project page for details on setting up your build system with the current Spring Cloud Release Train.  

  + Example spring boot app
  ```
   @SpringBootApplication
   @EnableFeignClients
   public class Application {
      public static void main(String[] args) {
          SpringApplication.run(Application.class, args);
      }
  }  
  ```
  
  + StoreClient.java. 
  ```
  @FeignClient("stores")
  public interface StoreClient {
      @RequestMapping(method = RequestMethod.GET, value = "/stores")
      List<Store> getStores();

      @RequestMapping(method = RequestMethod.POST, value = "/stores/{storeId}", consumes = "application/json")
      Store update(@PathVariable("storeId") Long storeId, Store store);
  }
  ```
  
  + Spring Cloud lets you take full control of the feign client by declaring additional configuration (on top of the 
  FeignClientsConfiguration) using @FeignClient. Example:
  ```
  @FeignClient(name = "stores", configuration = FooConfiguration.class)
  public interface StoreClient {
      //..
  }
  ```
  
  + Placeholders are supported in the name and url attributes. 
  ```
  @FeignClient(name = "${feign.name}", url = "${feign.url}")
  public interface StoreClient {
      //..
  }
  ```

#### 2.Feign Inheritance Support
Feign supports boilerplate apis via single-inheritance interfaces. This allows grouping common operations into 
convenient base interfaces.  

  + UserService.java. 
  ```
  public interface UserService {
      @RequestMapping(method = RequestMethod.GET, value ="/users/{id}")
      User getUser(@PathVariable("id") long id);
  }
  ```
  
  + UserResource.java. 
  ```
  @RestController
  public class UserResource implements UserService {

  }
  ```
  
  + UserClient.java. 
  ```
  package project.user;
  @FeignClient("users")
  public interface UserClient extends UserService {

  }
  ```
  
  
