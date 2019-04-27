package com.example.business.page;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * 
 * @author someone
 *
 * @param <T> A DAO from the Database
 */
public abstract class EntityPageResult<E> implements Page<E>{
	
    public static final long DEFAULT_OFFSET = 0;
    public static final int DEFAULT_MAX_NO_OF_ROWS = 100;
    
    private int offset;
    private int limit;
    private long totalElements;
    private List<E> elements;
    
    public EntityPageResult(List<E> elements, long totalElements, int offset, int limit) {
        this.elements = elements;
        this.totalElements = totalElements;
        this.offset = offset;
        this.limit = limit;
    }
    
    public boolean hasMore() {
        return totalElements > offset + limit;
    }
    
    public boolean hasPrevious() {
        return offset > 0 && totalElements > 0;
    }
    
    public long getTotalElements() {
        return totalElements;
    }
    
    public int  getOffset() {
        return offset;
    }
    
    public int getLimit() {
        return limit;
    }
    
    public List<E> getElements() {
        return elements;
    }

	@Override
	public int getNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumberOfElements() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<E> getContent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasContent() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Sort getSort() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isFirst() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLast() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Pageable nextPageable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pageable previousPageable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalPages() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <U> Page<U> map(Function<? super E, ? extends U> converter) {
		// TODO Auto-generated method stub
		return null;
	}
}