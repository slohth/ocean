package dev.slohth.ocean.utils.framework.database

import com.mongodb.MongoClient
import com.mongodb.client.MongoCollection
import dev.slohth.ocean.utils.framework.database.Database
import java.util.Locale
import com.mongodb.MongoClientURI
import org.bson.Document

enum class Database {

    RANKS, PROFILES;

    fun getCollection(): MongoCollection<Document> {
        return client!!.getDatabase("ocean").getCollection(name.lowercase())
    }

    companion object {
        var client: MongoClient? = null
        fun connect() { client = MongoClient(MongoClientURI("mongodb://localhost:27017/ocean")) }
        fun close() { client!!.close(); client = null }
    }

}