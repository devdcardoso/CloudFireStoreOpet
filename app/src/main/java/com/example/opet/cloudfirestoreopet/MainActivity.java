package com.example.opet.cloudfirestoreopet;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText editNome, editValor;
    private Spinner spinnerCategoria;
    private ListView listaProdutos;
    private TextView textTotal;

    private CheckBox checkAtivo;

    private FirebaseFirestore db;
    private List<String> produtos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editNome = findViewById(R.id.editNome);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        listaProdutos = findViewById(R.id.listProduto);
        produtos = new ArrayList<>();
        textTotal = findViewById(R.id.textTotal);
        editValor = findViewById(R.id.editValor);

        checkAtivo = findViewById(R.id.checkAtivo);

        db = FirebaseFirestore.getInstance();
    }

    @Override
    protected void onStart(){
        super.onStart();
        recuperarDados();
    }

    public void recuperarDados(){
        db.collection("Produtos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            produtos.clear();
                            listaProdutos.setAdapter(null);
                            double total = 0;
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Map<String,Object> objeto = document.getData();
                                //for(Map<String,Object> dados : obj)
                                Produto prod = new Produto();
                                prod.setNome(objeto.get("Nome").toString());
                                prod.setCategoria(objeto.get("Categoria").toString());
                                prod.setValor(Double.parseDouble(objeto.get("Valor").toString()));
                                produtos.add(prod.toString());
                                total += prod.getValor();
                            }
                            ArrayAdapter myAdapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,produtos);
                            listaProdutos.setAdapter(myAdapter);
                            String valor_formatado = "R$ %.2f";
                            textTotal.setText(String.format(valor_formatado, total));
                        }else{
                            Toast.makeText(MainActivity.this, "Não foi possivel recuperar os dados.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void salvarFirebase(View view) {
        Map<String,Object> produto = new HashMap<>();
        produto.put("Nome",editNome.getText().toString());
        produto.put("Categoria",spinnerCategoria.getSelectedItem().toString());
        produto.put("Valor",Double.parseDouble(editValor.getText().toString()));
        produto.put("Ativo",checkAtivo.isChecked());

        db.collection("Produtos")
                .add(produto)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(MainActivity.this, "Produto " + documentReference.getId() + " Adicionado.", Toast.LENGTH_SHORT).show();
                        recuperarDados();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Produto não adicionado.", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
