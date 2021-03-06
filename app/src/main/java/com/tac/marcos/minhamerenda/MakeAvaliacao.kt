package com.tac.marcos.minhamerenda

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.RatingBar
import android.widget.TextView
import com.example.marcos.okhttptest.Escola
import kotlinx.android.synthetic.main.activity_make_avaliacao.*
import okhttp3.OkHttpClient
import org.json.JSONObject
import java.net.SocketTimeoutException
import kotlin.concurrent.thread

class MakeAvaliacao : AppCompatActivity() {

    //VIEW
    var escolaTxt: TextView? = null
    var result: String? = null
    var pontuacaoStar: RatingBar? = null
    //Classes
    var escola: Escola? = null
    var user: Usuario? = null
    var ctrl_avaliacao = CtrlAvaliacao()
    //OKHTTP
    private var client: OkHttpClient? = null
    private val http = OkHttpClass()
    var urlPostAvaliacao = "minha-merenda.herokuapp.com/ts830/create/avaliacao"
    //JSON
    var avaliacaoJson: JSONObject = JSONObject()
    var avaliacaoList = ArrayList<Avaliacao>()
    //SharedPrefs
    var prefs: SharedPreferences? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_avaliacao)

        //VIEW
        escolaTxt = findViewById(R.id.txtEscola)
        pontuacaoStar = findViewById(R.id.starPontuacaoPost)

        //Getting Escola from previous Activity
        var bundle: Bundle = intent.extras
        escola = bundle.getSerializable("escola") as Escola?

        //Getting Usuário from previus Activity
        user = bundle.getSerializable("user") as Usuario

        //Get appKey and Token from SharedPrefs
        prefs = this.getSharedPreferences("mypref", Context.MODE_PRIVATE)
        var appkey = prefs!!.getString("appKey", "NULL")
        var token = prefs!!.getString("Token", "NULL")
        Log.i("Token", token)

        //Setting View
        escolaTxt!!.text = escola!!.getEscolaNome()

        Log.i("id", escola!!.getEscolaId().toString())
        Log.i("escolaCodigo", escola!!.getEscolaCodigo().toString())
        Log.i("Nome", escola!!.getEscolaNome())
        Log.i("Endereco", escola!!.getEndereco())
        Log.i("MecCodigo", escola!!.getMecCodigoe())
        Log.i("userID", user!!.getUsuarioID().toString())
        Log.i("userTipo", user!!.getTipo())



        btnPost.setOnClickListener {
            view -> Snackbar.make(view, "Enviando Avaliação. Aguarde.", Snackbar.LENGTH_INDEFINITE).setAction("Action", null).show()
            //Making Avaliacao
            avaliacaoJson.put("escola", JSONObject().put("id", escola!!.getEscolaId()))
            avaliacaoJson.put("usuario", JSONObject().put("id", user!!.getUsuarioID()))
            avaliacaoJson.put("pontuacao", pontuacaoStar!!.rating)
            avaliacaoJson.put("foto", "Link da foto")
            Log.i("JSONPOST", avaliacaoJson.toString())
            thread {
                try {
                    client = http.client
                    result = http.POSTurlHeader(client, avaliacaoJson.toString(), urlPostAvaliacao, appkey, token)

                    this.runOnUiThread {
                        Snackbar.make(view, result.toString(), Snackbar.LENGTH_LONG).setAction("Action", null).show()
                        Log.i("PostResult", result.toString())
                        thread{
                            try {
                                avaliacaoList = ctrl_avaliacao.getAll(prefs!!)!!
                                while (avaliacaoList.isEmpty()){

                                }
                                var bundle = Bundle()
                                bundle.putSerializable("escola", escola)
                                bundle.putSerializable("avaliacao", avaliacaoList)
                                startActivity(Intent(this, MainActivity::class.java).putExtras(bundle))
                                finish()
                            }catch (e : IllegalArgumentException){
//                              jsonTextField!!.text = e.message
                            }catch (a : SocketTimeoutException){
//                              jsonTextField!!.text = a.message
                            }
                        }
                    }

                }catch (e: IllegalArgumentException){
                    Snackbar.make(view, e.message.toString(), Snackbar.LENGTH_INDEFINITE).setAction("Action", null).show()
                }catch (b: SocketTimeoutException){
                    Snackbar.make(view, b.message.toString(), Snackbar.LENGTH_INDEFINITE).setAction("Action", null).show()
                }
            }

        }

    }
}
