package com.example.isafe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyListAdapter2 extends RecyclerView.Adapter<MyListAdapter2.ViewHolder> {
    List<MyListData> list;
    Context context;

  // RecyclerView recyclerView;
  public MyListAdapter2(List<MyListData> list, Context context) {
      this.list = list;
      this.context = context;
  }
  @Override
  public MyListAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View listItem= layoutInflater.inflate(R.layout.attendance_item, parent, false);
    MyListAdapter2.ViewHolder viewHolder2 = new MyListAdapter2.ViewHolder(listItem);
    return viewHolder2;
  }

  @Override
  public void onBindViewHolder(MyListAdapter2.ViewHolder holder, int position) {
    final MyListData myListData = list.get(position);
    holder.title.setText(myListData.getTitle());
    holder.event.setText(myListData.getEvent());
    holder.date.setText(myListData.getDate());
    holder.time.setText(myListData.getTime());
    holder.topic.setText(myListData.getTopic());
    holder.city.setText(myListData.getCity());

  }


  @Override
  public int getItemCount() {
      int arr = 0;

      try{
          if(list.size()==0){
              arr = 0;
          }
          else{
              arr=list.size();
          }
      }catch (Exception e){
          e.printStackTrace();
      }

      System.out.println("size" + arr);

      return arr;  }

  public static class ViewHolder extends RecyclerView.ViewHolder {


    public ImageView uni;
    public TextView title, event, date, time, topic, city ;

    Button markatt;
    ImageView like, send, msg;


    public ViewHolder(View itemView) {
      super(itemView);

      this.uni = (ImageView) itemView.findViewById(R.id.uni);

      this.title = (TextView) itemView.findViewById(R.id.title2);
      this.event = (TextView) itemView.findViewById(R.id.event);
      this.date = (TextView) itemView.findViewById(R.id.date);
      this.time = (TextView) itemView.findViewById(R.id.time);
      this.topic = (TextView) itemView.findViewById(R.id.topic);
      this.city = (TextView) itemView.findViewById(R.id.cityname);

      this.markatt = (Button) itemView.findViewById(R.id.markatt);

      this.like = (ImageView) itemView.findViewById(R.id.heart);
      this.send = (ImageView) itemView.findViewById(R.id.send);
      this.msg = (ImageView) itemView.findViewById(R.id.chatt);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = new Date();
        System.out.println(formatter.format(date1));

        if (formatter.format((date1)).equals(date.getText().toString())){

            markatt.setBackgroundResource(R.drawable.button_first_bg);


        }else {

            markatt.setBackgroundResource(R.drawable.reportbuttonbg);

        }

    }
  }
}