package tech.yashtiwari.firebasevideoapp.fragment.videostack;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import tech.yashtiwari.firebasevideoapp.model.VideoModel;

public class VideoStackViewModel extends ViewModel {

    public MutableLiveData<List<VideoModel>> mldVideos = new MutableLiveData<>();
    CollectionReference mCollection;

    public void getListOfVideos(){
        List<VideoModel> videos = new ArrayList<>();
        mCollection = FirebaseFirestore.getInstance().collection("videos");
        mCollection.orderBy("videoCreatedOn", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> obj = queryDocumentSnapshots.getDocuments();
                    if (obj.size() > 0) {
                        for (DocumentSnapshot snapshot : obj) {
                            VideoModel model = snapshot.toObject(VideoModel.class);
                            videos.add(model);
                        }
                    }

                }).addOnCompleteListener(task -> {
                    mldVideos.setValue(videos);

        });
    }


}
