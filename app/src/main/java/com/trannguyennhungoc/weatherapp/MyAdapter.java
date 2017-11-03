package com.trannguyennhungoc.weatherapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.util.List;

//import butterknife.BindView;
//import butterknife.ButterKnife;

/**
 * Created by thuyb on 18-Sep-17.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<WeatherResponse> responses;
    private Context contextParent;

    public MyAdapter(List<WeatherResponse> responses, Context contextParent) {
        this.responses = responses;
        this.contextParent = contextParent;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_one_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WeatherResponse response = responses.get(position);
        holder.Degree.setText(R.string.symbol_degree_Celsius);
        holder.Date.setText(response.get_Date().toString());
        holder.Description.setText(response.get_Description());
        holder.Place.setText(response.get_Place());
        holder.Temp.setText(String.valueOf(Math.round(response.get_Temp())));
        String sIcon = response.get_Icon();
        try {
            Picasso.with(contextParent)
                    .load(new Networking().buildUrlIcon(sIcon))
                    .into(holder.Icon);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return responses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Date;
        TextView Degree;
        TextView Description;
        TextView Place;
        TextView Temp;
        ImageView Icon;


        public ViewHolder(View itemView) {
            super(itemView);
//            ButterKnife.bind(this,itemView);
            Date = (TextView) itemView.findViewById(R.id.tv_date);
            Degree = (TextView) itemView.findViewById(R.id.tv_degree);
            Description = (TextView) itemView.findViewById(R.id.tv_description);
            Place = (TextView) itemView.findViewById(R.id.tv_place);
            Temp = (TextView) itemView.findViewById(R.id.tv_temp);
            Icon = (ImageView) itemView.findViewById(R.id.iv_icon);

        }
    }
}
