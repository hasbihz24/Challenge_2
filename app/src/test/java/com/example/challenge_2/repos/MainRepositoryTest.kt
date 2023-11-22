package com.example.challenge_2.repos

import android.app.backup.BackupAgentHelper
import com.example.challenge_2.api.APIClient
import com.example.challenge_2.api.APIService
import com.example.challenge_2.model.MenuResponse
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.kotlin.mock

internal class MainRepositoryTest {
private lateinit var service: APIService
private lateinit var client: APIClient
private lateinit var repo: MainRepository
    @Before
    fun setUp(){
        service = mockk()
        repo = MainRepository(service)
    }
    @Test
    fun getMenu(): Unit = runBlocking {
        val respAllMenu = mockk<MenuResponse>()

        every {
            runBlocking {
                service.getMenu2()
            }
        }returns respAllMenu

        repo.getMenu()

        verify {
            runBlocking { service.getMenu2()     }
        }

    }
}