package com.example.isafe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
  private MyListData[] listdata;

    List<MyListData> list;
    Context context;

  // RecyclerView recyclerView;
  public MyListAdapter(List<MyListData> list, Context context) {
    this.list = list;
    this.context = context;
  }
  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
    ViewHolder viewHolder = new ViewHolder(listItem);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    final MyListData myListData = list.get(position);
    holder.title.setText(myListData.getTitle());
    holder.event.setText(myListData.getEvent());
      holder.date.setText(myListData.getDate());
      holder.time.setText(myListData.getTime());
      holder.topic.setText(myListData.getTopic());

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



      }

      return arr;  }

  public static class ViewHolder extends RecyclerView.ViewHolder {


    public ImageView uni;
    public TextView title, event, date, time, topic ;

    Button register;
    ImageView like, send, msg;


    public ViewHolder(View itemView) {
      super(itemView);

      this.uni = (ImageView) itemView.findViewById(R.id.uni);

      this.title = (TextView) itemView.findViewById(R.id.title2);
      this.event = (TextView) itemView.findViewById(R.id.event);
      this.date = (TextView) itemView.findViewById(R.id.date);
      this.time = (TextView) itemView.findViewById(R.id.time);
      this.topic = (TextView) itemView.findViewById(R.id.topic);

      this.register = (Button) itemView.findViewById(R.id.register);

      this.like = (ImageView) itemView.findViewById(R.id.heart);
      this.send = (ImageView) itemView.findViewById(R.id.send);
      this.msg = (ImageView) itemView.findViewById(R.id.chatt);



    }
  }
}