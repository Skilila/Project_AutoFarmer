package com.example.autofarmer.crop.domain

import com.example.autofarmer.user.domain.User
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
@Table(
    name = "crop", schema = "smartfarmdb", uniqueConstraints = [
        UniqueConstraint(name = "name_UNIQUE", columnNames = ["name"])
    ]
)
class Crop(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var cropId: Int,//Crop테이블 PK,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id")
    var category: Category? = null, //작물 카테고리
    @Size(max = 30)
    @NotNull
    @Column(nullable = false, length = 30)
    var name: String,//작물 이름

    var temperature: Double? = null,//작물 현재 기온
    var humidity: Double? = null,//작물 현재 습도

    @Size(max = 10)
    @NotNull
    @Column(nullable = false, length = 10)
    var status: String = "NORMAL",
    var isFavorite: Boolean = false,


    @ManyToMany(mappedBy = "favoriteCrops")
    var favoredByUsers: MutableSet<User> = mutableSetOf()
)
