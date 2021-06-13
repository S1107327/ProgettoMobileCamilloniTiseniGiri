package com.example.progettomobilecamillonitisenigiri

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.progettomobilecamillonitisenigiri.Model.Corso
import com.example.progettomobilecamillonitisenigiri.Model.Lezione
import com.example.progettomobilecamillonitisenigiri.ViewModels.FirebaseConnection
import com.google.firebase.ktx.Firebase
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock



@RunWith(JUnit4::class)
class ProgettoUnitTest {


    lateinit var firebaseConnection: FirebaseConnection

    @Before
    fun setUp() {
        firebaseConnection = mock(FirebaseConnection::class.java)
        Log.d("ciao", firebaseConnection.getListaCorsi().toString())
    }


    @Test
    fun corsi_isOfCorrectClass() {

        assertTrue(firebaseConnection.getListaCorsi() is MutableLiveData<List<Corso>>)
    }

    @Test
    fun corsi_isNotNull() {
        assertNotNull(firebaseConnection.getListaCorsi())
    }

    @Test
    fun corso_isOfCorrectClass() {
        val corso = firebaseConnection.getListaCorsi().value?.get(0)
        assertTrue(corso is Corso)

    }

    @Test
    fun corsoSpecifico_hasCorrectValues() {
        val corso = firebaseConnection.getListaCorsi().value?.get(10)
        assertEquals("Aerobica a casa", corso?.titolo)
        assertEquals("100€", corso?.prezzo)
        assertEquals("Mario Rossi", corso?.docente)
        assertNotNull(corso?.lezioni)
    }


    @Test
    fun lezioni_isOfCorrectClass() {
        assertTrue(firebaseConnection.getListaLezioni() is MutableLiveData<HashMap<String, ArrayList<Lezione>>>)
    }

    @Test
    fun lezioni_isNotNull() {
        assertNotNull(firebaseConnection.getListaLezioni())
    }

    @Test
    fun lezione_isOfCorrectClass() {
        val lezione = firebaseConnection.getListaLezioni().value?.get("0")?.get(0)
        assertTrue(lezione is Lezione)

    }

    @Test
    fun lezioneSpecifica_hasCorrectValues() {
        val lezione = firebaseConnection.getListaLezioni().value?.get("7")?.get(1)
        assertEquals("Lezione 2: Cose particolari della fotografia", lezione?.titolo)
        assertEquals("lezione2", lezione?.id)
        assertEquals("FoHE1f1_fCE", lezione?.url)
    }

}

