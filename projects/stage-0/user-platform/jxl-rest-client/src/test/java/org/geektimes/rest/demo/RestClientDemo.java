package org.geektimes.rest.demo;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Entity;

public class RestClientDemo {

    public static void main(String[] args) {
       doGet();
        //doPost();

    }

    private static void doPost() {
        Client client = ClientBuilder.newClient();

        Form form = new Form();
        form.param("name","太极张三丰");
        form.param("phoneNumber","15802221002");
        form.param("email","xx@qq.com");
        form.param("password","123456");
        Response response = client
                .target("http://localhost:8080/user/account?methodName=register")      // WebTarget
                .request() // Invocation.Builder
                .post(Entity.form(form));                                     //  Response

        String content = response.readEntity(String.class);

        System.out.println(content);
    }

    private static void doGet() {
        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://www.baidu.com")      // WebTarget
                .request() // Invocation.Builder
                .get();                                     //  Response

        String content = response.readEntity(String.class);

        System.out.println(content);
    }
}
