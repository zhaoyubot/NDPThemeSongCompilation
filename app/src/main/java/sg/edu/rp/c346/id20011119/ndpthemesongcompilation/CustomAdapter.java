package sg.edu.rp.c346.id20011119.ndpthemesongcompilation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    Context parent_context;
    int layout_id;
    ArrayList<Song> SongList;

    public CustomAdapter(@NonNull Context context, int resource,
                         ArrayList<Song> objects) {
        super(context, resource, objects);

        parent_context = context;
        layout_id = resource;
        SongList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)
                parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(layout_id,parent,false);
        TextView tvName =rowView.findViewById(R.id.textViewTitle);
        TextView tvYearReleased = rowView.findViewById(R.id.textViewyearReleased);
        TextView tvSinger = rowView.findViewById(R.id.textViewSingers);
        Song currentVersion = SongList.get(position);
        tvName.setText(currentVersion.getTitle());
        tvYearReleased.setText(currentVersion.getYear() + "");
        tvSinger.setText(currentVersion.getSingers());
        String numstars = "";
        if(currentVersion.getStars() == 1){
            numstars = "*";
        }else if(currentVersion.getStars() == 2){
            numstars = "**";
        }else if(currentVersion.getStars() == 3){
            numstars = "***";
        }else if(currentVersion.getStars() == 4){
            numstars = "****";
        }else if(currentVersion.getStars() == 5){
            numstars = "*****";
        }
        return rowView;
    }

}
