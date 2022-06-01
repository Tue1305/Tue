/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lexor.pos.web.jersey;


import java.util.ArrayList;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

@Singleton
@Path("/message")
public class JerseyService
{
    //https://psamsotha.github.io/jersey/2015/10/18/http-caching-with-jersey.html
    
    String data = "CacheService";
    
    @GET
    public String getMsg()
    {
         return "Hello World !! - Jersey 2";
    }
    
    @GET
    @Path("/CacheService/{userId}")
    public Response invokeCacheService(@PathParam("userId") String userId, @Context Request req){

            //the key of a good cache control technique, is to : be quick in order to determine if present or not in cache, 
            //and to try to avoid the maximum data processing in order to retrieve fromthe cache (example avoid performing getPlaylistSong  under cache
           int TTL_CACHE_SONGS=10000; //in ms
           String tag =  data + userId;

           //is under cache ?
           Response r = HttpCacheRizze.getCachedResponseMilliseconds(req, tag, TTL_CACHE_SONGS);
           if(r!=null){
                // under cache
                return r;
           }

           // cache is not present or need to be refreshed

            String value = this.data;
            
            int status = 200;

            //catch here errors .... empty....
            if(value==null)
                status = 204;

            //r = HttpCacheRizze.getCacheInvalidatedResponse(status, new Gson().toJson(songList), tag, TTL_CACHE_SONGS);

            r = HttpCacheRizze.getCacheInvalidatedResponse(status, value, tag, TTL_CACHE_SONGS);

            return r;

    }
    @POST
    @Consumes("text/plain")
    public Response post(String data) {
        
        this.data = "new data";
        
        return Response.noContent().build();
    }
}