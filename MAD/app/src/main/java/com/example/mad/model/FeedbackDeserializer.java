package com.example.mad.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.sql.Timestamp;

public class FeedbackDeserializer implements JsonDeserializer<Feedback> {
    @Override
    public Feedback deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        int id = jsonObject.get("id").getAsInt();
        User user = context.deserialize(jsonObject.get("user"), User.class);
        Event event = context.deserialize(jsonObject.get("event"), Event.class);
        String message = jsonObject.get("message").getAsString();

        // Kiểm tra nếu trường "type" là null, thì gán một giá trị mặc định
        JsonElement typeElement = jsonObject.get("type");
        FeedbackType type = typeElement.isJsonNull() ? FeedbackType.NEUTRAL : context.deserialize(typeElement, FeedbackType.class);

        // Deserialize trường createdTime
        Timestamp createdTime = context.deserialize(jsonObject.get("createdTime"), Timestamp.class);

        return new Feedback(id, user, event, type, message, createdTime);
    }
}

