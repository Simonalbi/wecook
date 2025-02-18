package com.wecook.rest.serializers;

import com.google.gson.*;
import com.wecook.model.*;
import com.wecook.rest.utils.CustomGson;
import com.wecook.rest.utils.RequestParser;

import java.lang.reflect.Type;
import java.util.Base64;

public class PostSerializer implements JsonSerializer<Post> {

    @Override
    public JsonElement serialize(Post post, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        Gson gson = CustomGson.getInstance().getGson();

        jsonObject.addProperty("id", post.getId());

        String postPictureEncoded = RequestParser.byteArrayToBase64(post.getPostPicture());
        jsonObject.addProperty("picture", postPictureEncoded);

        jsonObject.addProperty("likes", post.getLikes().size());

        jsonObject.add("user", gson.toJsonTree(post.getStandardUser()));

        jsonObject.add("recipe", JsonNull.INSTANCE);
        if (post.getRecipe() != null) {
            JsonElement recipe = gson.toJsonTree(post.getRecipe());
            jsonObject.add("recipe", recipe);

            int totalDuration = post.getRecipe().getSteps()
                    .stream()
                    .mapToInt(Step::getDuration)
                    .sum();

            jsonObject.add("duration", new JsonPrimitive(totalDuration));
        }

        JsonArray comments = new JsonArray();
        for (Comment comment : post.getComments()) {
            JsonObject jsonRecipeIngredient = gson.toJsonTree(comment).getAsJsonObject();
            comments.add(jsonRecipeIngredient);
        }
        jsonObject.add("comments", comments);

        return jsonObject;
    }
}
