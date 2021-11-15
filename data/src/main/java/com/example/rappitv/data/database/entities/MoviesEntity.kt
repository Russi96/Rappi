package com.example.rappitv.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rappitv.domain.ResultX
import com.example.rappitv.utils.Constants.MOVIES_TABLE

@Entity(tableName = MOVIES_TABLE)
class MoviesEntity(
    var movies: ResultX
) {

    var id: Int = movies.id
    @PrimaryKey
    var name: String = movies.title
}