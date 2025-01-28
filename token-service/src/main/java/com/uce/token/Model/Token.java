package com.uce.token.Model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "Token")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    @Id
    private ObjectId id;
    private String token;
    private String userId;
    private long expiryDate;
    

}
