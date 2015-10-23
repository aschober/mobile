package us.lessig2016.android.api;

import retrofit.Call;
import retrofit.http.GET;
import us.lessig2016.android.api.models.Feed;

public interface Lessig2016Api {
    // Request method and URL specified in the annotation
    // Callback for the parsed response is the last parameter

    @GET("greetings/hello")
    Call<Feed> getFeed();

}