package br.com.immunize.navigationdrawer.NAVI.Diario;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;

import br.com.immunize.navigationdrawer.R;


public class CameraFotoFragment extends Fragment implements View.OnClickListener, ViewTreeObserver.OnGlobalLayoutListener {

    File mCaminhoFoto;
    ImageView mImageViewFoto;
    CarregarImageTask mTask;
    int mLarguraImage;
    int mAlturaImage;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        String caminhoFoto = Util.carregarUltimaMidia(getActivity(), Util.MIDIA_FOTO);

        if (caminhoFoto != null){
            mCaminhoFoto = new File(caminhoFoto);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_gravar_audio, container, false);

        layout.findViewById(R.id.btnFoto).setOnClickListener(this);
        mImageViewFoto = (ImageView) layout.findViewById(R.id.imgFoto);
        layout.getViewTreeObserver().addOnGlobalLayoutListener(this);
        return layout;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK && requestCode == Util.REQUESTCODE_FOTO){
            carregarImagem();
        }
    }

    @Override
    public void onGlobalLayout(){
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1){
            getView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
        } else{
            getView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
        mLarguraImage = mImageViewFoto.getWidth();
        mAlturaImage = mImageViewFoto.getHeight();
        carregarImagem();
    }

        @Override
        public  void onClick(View v){
            if(v.getId() == R.id.btnFoto){
                mCaminhoFoto = Util.novaMidia(Util.MIDIA_FOTO);

                Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCaminhoFoto));

                startActivityForResult(it, Util.REQUESTCODE_FOTO);
            }
        }

    private void carregarImagem(){
        if(mCaminhoFoto != null && mCaminhoFoto.exists()){
            if(mTask == null || mTask.getStatus() != AsyncTask.Status.RUNNING){
                mTask = new CarregarImageTask();
                mTask.execute();
            }
        }
    }

    class CarregarImageTask extends AsyncTask<Void, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Void... voids){
            return Util.carregarImagem(mCaminhoFoto, mLarguraImage, mAlturaImage);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap){
        super.onPostExecute(bitmap);

            if(bitmap != null){
                mImageViewFoto.setImageBitmap(bitmap);
                if (getActivity() != null) {
                    Util.salvarUltimaMidia(getActivity(), Util.MIDIA_FOTO, mCaminhoFoto.getAbsolutePath());
                }
            }
        }
    }

}
