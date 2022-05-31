package com.csiris.postservice.persistence

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.*;

// "Exposed" Kotlin ORM Framework can also be used.But decided to go with JPA
// https://github.com/JetBrains/Exposed
@MappedSuperclass
abstract class BaseEntity {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	//@JsonIgnore
	var id: Long? = null

	//@CreatedDate
	//@CreationTimestamp
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd hh:mm:ss")
	@Column(name = "CREATED_AT")
	var createdAt: LocalDateTime? = LocalDateTime.now()

	//@LastModifiedBy
	//@UpdateTimestamp
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd hh:mm:ss")
	@Column(name = "UPDATED_AT")
	var updatedAt: LocalDateTime? = LocalDateTime.now()

	override fun toString(): String {
		return " 'id : $id , createdAt : $createdAt , updatedAt : $updatedAt' )"
	}
}