package com.example.secondtest;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class PageDetailProduits extends AppCompatActivity {

    TextView titleProduct;
    TextView productName;
    TextView productCategory;
    TextView productPrice;
    TextView productWebsite;
    TextView productInfo;
    private Button likeButton;
    private Button dislikeButton;
    private ImageView unfillLike;
    private ImageView fillLike;
    private String checkLike = "unfillLike";
    private ImageView unfillDislike;
    private ImageView fillDislike;
    private String checkDislike = "unfillDislike";

    ProductDAO productDAO;
    Cursor aboutProduct;
    String productNb;
    private String login;
    private String wishListNb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_detail_produits);

        this.titleProduct = (TextView) findViewById(R.id.titleProduct);
        this.productName = (TextView) findViewById(R.id.setName);
        this.productCategory = (TextView) findViewById(R.id.setCategory);
        this.productPrice = (TextView) findViewById(R.id.setPrice);
        this.productWebsite = (TextView) findViewById(R.id.setWebsite);
        this.productInfo = (TextView) findViewById(R.id.setInfo);
        this.unfillLike = (ImageView) findViewById(R.id.unfillLike);
        this.fillLike = (ImageView) findViewById(R.id.fillLike);
        this.unfillDislike = (ImageView) findViewById(R.id.unfillDislike);
        this.fillDislike = (ImageView) findViewById(R.id.fillDislike);

        this.productDAO = new ProductDAO(this);
        this.productNb = productDAO.getProductNumber(getIntent().getStringExtra("PRODUCTNAME1"));
        this.wishListNb = getIntent().getStringExtra("WISHLISTNB_DETAILS_PRODUIT");
        this.aboutProduct = productDAO.getAllColumn(productNb);
        this.login = getIntent().getStringExtra("LOGIN_DETAILS_PRODUIT");

        this.aboutProduct.moveToFirst();

        this.titleProduct.setText(aboutProduct.getString(1));
        this.productName.setText(aboutProduct.getString(1));
        this.productPrice.setText(aboutProduct.getString(3));
        this.productInfo.setText(aboutProduct.getString(4));
        this.productCategory.setText(aboutProduct.getString(5));
        this.productWebsite.setText(aboutProduct.getString(6));

        showToast(productNb);
        //Permet de récupérer la réaction qui avait déjà été définie auparavant pour le produit
        /*byte likeStatus = Byte.parseByte(productDAO.getLikeStatus(login,wishListNb,productNb));
        if (likeStatus == 1){
            fillLike.setVisibility(View.VISIBLE);
            unfillLike.setVisibility(View.GONE);
            checkLike = "fillLike";
        }
        else if (likeStatus == -1){
            fillDislike.setVisibility(View.VISIBLE);
            unfillDislike.setVisibility(View.GONE);
            checkLike = "fillDislike";
        }*/

    }

    public void unfilledLike(View view){
        if(checkDislike == "unfillDislike"){
            if (checkLike.equals("unfillLike")){
                animation(fillLike);
                fillLike.setVisibility(View.VISIBLE);
                unfillLike.setVisibility(View.GONE);
            }
            checkLike="fillLike";
            productDAO.updateLike(login, wishListNb, productNb, (byte) 1.0);
        }
        else{
            showToast("You already put a thumb down");
        }
    }

    public void filledLike(View view){
        if (checkLike.equals("fillLike")){
            fillLike.setVisibility(View.GONE);
            unfillLike.setVisibility(View.VISIBLE);
        }
        checkLike="unfillLike";
        productDAO.updateLike(login, wishListNb, productNb, (byte) 0.0);
    }

    public void unfilledDislike(View view){
        if(checkLike == "unfillLike") {
            if (checkDislike.equals("unfillDislike")) {
                animation(fillDislike);
                fillDislike.setVisibility(View.VISIBLE);
                unfillDislike.setVisibility(View.GONE);
            }
            checkDislike = "fillDislike";
            productDAO.updateLike(login, wishListNb, productNb, (byte) -1.0);
        }
        else{
            showToast("You already put a thumb up");
        }
    }

    public void filledDislike(View view){
        if (checkDislike.equals("fillDislike")){
            fillDislike.setVisibility(View.GONE);
            unfillDislike.setVisibility(View.VISIBLE);
        }
        checkDislike="unfillDislike";
        productDAO.updateLike(login, wishListNb, productNb, (byte) 0.0);
    }

    private static Animation prepareAnimation(Animation animation){
        animation.setRepeatCount(0);
        animation.setRepeatMode(Animation.REVERSE);
        return animation;
    }

    public static void animation(final ImageView view){
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f,1.0f,0.0f,1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        prepareAnimation(scaleAnimation);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f,1.0f);
        prepareAnimation(alphaAnimation);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.setDuration(500);
        view.startAnimation(animationSet);
    }

    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}