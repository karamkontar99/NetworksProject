package edu.networks.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.networks.project.models.Document;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.DocumentViewHolder> {
    private final Context context;
    private OnDocumentClickListener listener;
    private List<Document> documents;

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
        notifyDataSetChanged();
    }

    public void setOnDocumentClickListener(OnDocumentClickListener listener) {
        this.listener = listener;
    }

    public DocumentAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public DocumentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.document_list_item, parent, false);
        return new DocumentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DocumentViewHolder holder, int i) {
        final int index = i;
        final Document document = documents.get(index);
        holder.bind(document);
        holder.itemView.setOnClickListener(view -> listener.onDocumentClick(document, index));
    }

    @Override
    public int getItemCount() {
        return documents.size();
    }

    class DocumentViewHolder extends RecyclerView.ViewHolder {
        private TextView fileName, fileSize;

        public DocumentViewHolder(@NonNull View itemView) {
            super(itemView);
            fileName = itemView.findViewById(R.id.textView_document_name);
            fileSize = itemView.findViewById(R.id.textView_document_size);
        }

        public void bind(Document document) {
            fileName.setText(document.getName());
            fileSize.setText(document.getSize() + "B");
        }
    }

    public interface OnDocumentClickListener {
        void onDocumentClick(Document document, int index);
    }
}
