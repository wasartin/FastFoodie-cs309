package edu.iastate.graysonc.fastfood.database.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultList {
    @SerializedName("content")
    @Expose
    private List<Food> content;

    @SerializedName("pageable")
    @Expose
    private Pageable pageable;

    @SerializedName("totalElements")
    @Expose
    private int totalElements;

    @SerializedName("last")
    @Expose
    private boolean isLast;

    @SerializedName("totalPages")
    @Expose
    private int totalPages;

    @SerializedName("first")
    @Expose
    private boolean isFirst;

    public ResultList(List<Food> content, Pageable pageable, int totalElements, boolean isLast, int totalPages, boolean isFirst) {
        this.content = content;
        this.pageable = pageable;
        this.totalElements = totalElements;
        this.isLast = isLast;
        this.totalPages = totalPages;
        this.isFirst = isFirst;
    }

    public List<Food> getContent() {
        return content;
    }

    public void setContent(List<Food> content) {
        this.content = content;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public class Pageable {
        @SerializedName("sort")
        @Expose
        private Sort sort;

        @SerializedName("pageSize")
        @Expose
        private int pageSize;

        @SerializedName("pageNumber")
        @Expose
        private int pageNumber;

        @SerializedName("offset")
        @Expose
        private int offset;

        @SerializedName("paged")
        @Expose
        private boolean isPaged;

        @SerializedName("unpaged")
        @Expose
        private boolean isUnaged;

        public Pageable(Sort sort, int pageSize, int pageNumber, int offset, boolean isPaged, boolean isUnaged) {
            this.sort = sort;
            this.pageSize = pageSize;
            this.pageNumber = pageNumber;
            this.offset = offset;
            this.isPaged = isPaged;
            this.isUnaged = isUnaged;
        }

        public Sort getSort() {
            return sort;
        }

        public void setSort(Sort sort) {
            this.sort = sort;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageNumber() {
            return pageNumber;
        }

        public void setPageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public boolean isPaged() {
            return isPaged;
        }

        public void setPaged(boolean paged) {
            isPaged = paged;
        }

        public boolean isUnaged() {
            return isUnaged;
        }

        public void setUnaged(boolean unaged) {
            isUnaged = unaged;
        }
    }

    public class Sort {
        @SerializedName("sorted")
        @Expose
        private boolean isSorted;

        @SerializedName("unsorted")
        @Expose
        private boolean isUnsorted;

        @SerializedName("empty")
        @Expose
        private boolean isEmpty;

        public Sort(boolean isSorted, boolean isUnsorted, boolean isEmpty) {
            this.isSorted = isSorted;
            this.isUnsorted = isUnsorted;
            this.isEmpty = isEmpty;
        }

        public boolean isSorted() {
            return isSorted;
        }

        public void setSorted(boolean sorted) {
            isSorted = sorted;
        }

        public boolean isUnsorted() {
            return isUnsorted;
        }

        public void setUnsorted(boolean unsorted) {
            isUnsorted = unsorted;
        }

        public boolean isEmpty() {
            return isEmpty;
        }

        public void setEmpty(boolean empty) {
            isEmpty = empty;
        }
    }
}
