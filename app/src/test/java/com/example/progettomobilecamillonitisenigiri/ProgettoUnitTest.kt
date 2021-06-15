package com.example.progettomobilecamillonitisenigiri

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.progettomobilecamillonitisenigiri.Model.Corso
import com.example.progettomobilecamillonitisenigiri.Model.Lezione
import com.example.progettomobilecamillonitisenigiri.Model.UltimaLezione
import com.example.progettomobilecamillonitisenigiri.Model.User
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
    var corsi = ArrayList<Corso>()
    lateinit var user:User

    @Before
    fun setUp() {
        corsi.add(Corso("Java per principianti","Impara le basi del java","Adriano Mancini","0","https://corsi-informatica.com/wp-content/upload...","10€","Informatica", HashMap(), ArrayList(),ArrayList(), ArrayList()
        ))
        corsi.add(Corso("Cuciniamo insieme a Tiso","Impara a cucinare","","1","https://www.aiafood.com/sites/default/files/sty...","10€","robotica", HashMap(), ArrayList(),ArrayList(),ArrayList()))
        corsi.add(Corso("Robotics course","Robots are cool! We can't imagine the world today can run without the help of robots. Robots are every where - right from cleaning homes, cooking food, to assembling cars in huge manufacturing plants and defence. Robots have found a place in almost every possible ","AndrewNG","3","https://i0.wp.com/www.alzarating.com/wp-content/uploads/2019/07/alzarating-58.jpg?fit=800%2C351&ssl=1","10€","cucina", HashMap(), ArrayList(),ArrayList(),ArrayList()))
        corsi.add(Corso("Corso base di design","Il corso intende formare un professionista che ...","","4","https://lifelearning.it/wp-content/uploads/2015/03/interior-design.png","10€","design", HashMap(), ArrayList(),ArrayList(),ArrayList()))
        corsi.add(Corso("Corso di marketing per principianti","Corso base sul marketing. In questo corso imparerai a diventare un vero genio del marketing!","","5","https://www.mirkocuneo.it/wp-content/uploads/2019/12/web-marketing-per-imprenditori-1024x632.jpg","","marketing",HashMap(), ArrayList(),ArrayList(),ArrayList()))
        var iscrizioni = ArrayList<String>()
        with(iscrizioni){
            add("1")
            add("19")
            add("0")
        }
        var catPref = ArrayList<String>()
        with(catPref){
            add("Informatica")
            add("design")
        }
        var wishlist = ArrayList<String>()
        with(wishlist){
            add("5")
            add("4")
            add("3")
        }
        user = User("Lorenzo","Tiseni",iscrizioni,catPref,wishlist,ArrayList(), UltimaLezione())
    }


    @Test
    fun getListaConsigliati() {
        val consigliati = ArrayList<Corso>()
        for (corso in corsi) {
            for (categoria in user.categoriePref) {
                if (corso.categoria.equals(categoria)) {
                    consigliati.add(corso)
                }
            }
        }
        var corsiPrev = ArrayList<Corso>()
        corsiPrev.add(Corso("Java per principianti","Impara le basi del java","Adriano Mancini","0","https://corsi-informatica.com/wp-content/upload...","10€","Informatica", HashMap(), ArrayList(),ArrayList(), ArrayList()
        ))
        corsiPrev.add(Corso("Corso base di design","Il corso intende formare un professionista che ...","","4","https://lifelearning.it/wp-content/uploads/2015/03/interior-design.png","10€","design", HashMap(), ArrayList(),ArrayList(),ArrayList()))
        assertEquals(corsiPrev,corsi)
    }

/*
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
*/
}

