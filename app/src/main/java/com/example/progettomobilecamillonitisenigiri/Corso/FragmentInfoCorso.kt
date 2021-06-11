package com.example.progettomobilecamillonitisenigiri.Corso

import android.app.AlertDialog
import android.content.DialogInterface
import android.media.Rating
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.progettomobilecamillonitisenigiri.Model.Corso
import com.example.progettomobilecamillonitisenigiri.R
import com.example.progettomobilecamillonitisenigiri.ViewModels.FirebaseConnection
import com.squareup.picasso.Picasso
import kotlinx.coroutines.awaitAll
import java.lang.Exception

class FragmentInfoCorso : Fragment() {
    //val corsoModel: CorsiViewModel by viewModels()
    val firebaseConnection: FirebaseConnection by viewModels()
    private var ratingBarIsInitialized = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info_corso, container, false)
    }

    override fun onResume() {
        super.onResume()
        val id = requireActivity().intent.getStringExtra("ID_CORSO")
        firebaseConnection.getListaCorsi()
            .observe(viewLifecycleOwner, Observer<List<Corso>> { corsi ->
                for (a in corsi)
                    if (a.id.equals(id)) {
                        view?.findViewById<TextView>(R.id.nomeCorso)?.text = a.titolo
                        view?.findViewById<TextView>(R.id.descrizioneCorso)?.text = a.descrizione
                        view?.findViewById<TextView>(R.id.categoriaCorso)?.text = a.categoria
                        view?.findViewById<TextView>(R.id.numLezioni)?.text =
                            a.lezioni.size.toString()
                        view?.findViewById<TextView>(R.id.docenteCorso)?.text = a.docente
                        view?.findViewById<TextView>(R.id.textView15)?.text = a.prezzo

                        //Inizializzo la ratingBar con il valore medio delle recensioni associate corso
                        if (!ratingBarIsInitialized) {
                            view?.findViewById<RatingBar>(R.id.rating)?.rating =
                                a.recensioni.values.average().toFloat()
                            if(a.recensioni.size==1)
                                view?.findViewById<TextView>(R.id.textView2)?.text="(${a.recensioni.size.toString()} Recensione)"
                            else view?.findViewById<TextView>(R.id.textView2)?.text="(${a.recensioni.size.toString()} Recensioni)"
                            ratingBarIsInitialized = true
                        }
                        // Disabilito la ratingBar se l'utente non è iscritto
                        view?.findViewById<RatingBar>(R.id.rating)?.setIsIndicator(true)

                        //Se l'utente è iscritto al corso la ratingBar viene abilitata e avrà la possibilià di dare una recensione
                        if (firebaseConnection.getUser().value!!.iscrizioni.contains(id)) {
                            view?.findViewById<RatingBar>(R.id.rating)?.setIsIndicator(false) //abilita ratingBar
                            view?.findViewById<RatingBar>(R.id.rating)?.setOnRatingBarChangeListener(object : RatingBar.OnRatingBarChangeListener {
                                    override fun onRatingChanged(ratingBar: RatingBar?, recensione: Float, isChangeFromUser: Boolean) {
                                        //Controllo se il cambio stato della ratingBar è dovuto dall'azione dell'utente
                                        if(isChangeFromUser) {
                                            Toast.makeText(
                                                context,
                                                "Grazie per la sua recensione: $recensione stelle",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        //Aggiorno la recensione o la aggiungo se assente nel db
                                        firebaseConnection.setRecensione(id, recensione)
                                        ratingBar?.rating = a.recensioni.values.average().toFloat() //setto il valore medio delle recensioni presenti
                                        if(a.recensioni.size==1)
                                            view?.findViewById<TextView>(R.id.textView2)?.text="(${a.recensioni.size.toString()} Recensione)"
                                        else view?.findViewById<TextView>(R.id.textView2)?.text="(${a.recensioni.size.toString()} Recensioni)"

                                    }

                                })

                        }

                        //Aggiorno il bottone di iscrizione a seconda se l'utente è iscritto o meno
                        if (firebaseConnection.getUser().value!!.iscrizioni.contains(id)) {
                            view?.findViewById<Button>(R.id.iscrivitiButton)?.text =
                                "ANNULLA ISCRIZIONE"
                            view?.findViewById<Button>(R.id.iscrivitiButton)?.setTextColor(
                                -65536
                            )
                        } else {
                            view?.findViewById<Button>(R.id.iscrivitiButton)?.text = "ISCRIVITI"
                            view?.findViewById<Button>(R.id.iscrivitiButton)?.setTextColor(
                                -10354450
                            )
                        }
                        view?.findViewById<Button>(R.id.iscrivitiButton)?.setOnClickListener {
                            firebaseConnection.iscriviti(id) //al click del bottone l'utente viene iscritto/tolto al/dal corso
                        }

                        //Operazioni su bottone wishlist
                        if (firebaseConnection.getUser().value!!.wishlist.contains(id)) {
                            view?.findViewById<ImageButton>(R.id.bottoneWishlist)
                                ?.setImageResource(R.drawable.wishlistfull36dp)
                        } else {
                            view?.findViewById<ImageButton>(R.id.bottoneWishlist)
                                ?.setImageResource(R.drawable.wishlistempty36dp)

                        }
                        view?.findViewById<ImageButton>(R.id.bottoneWishlist)?.setOnClickListener {
                            firebaseConnection.wishlist(id) //al click si aggiunge/elimina il corso dalla wishlist
                        }


                        try {
                            Picasso.get().load(a.immagine)
                                .into(view?.findViewById<ImageView>(R.id.imageCorso))
                        } catch (e: Exception) {
                            Log.d("Eccezione", "Eccezione: Problemi caricamento immagine")
                            Picasso.get()
                                .load("https://png.pngtree.com/png-vector/20191120/ourmid/pngtree-training-course-online-computer-chat-flat-color-icon-vector-png-image_2007114.jpg")
                                .into(view?.findViewById<ImageView>(R.id.imageCorso))
                        }

                    }
            })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getString("ID_CORSO")
        if (id != null) {
            Log.d("CiaoId", id)
        }


    }

}