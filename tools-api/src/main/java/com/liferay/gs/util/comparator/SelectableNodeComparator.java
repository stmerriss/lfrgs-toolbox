package com.liferay.gs.util.comparator;

import com.liferay.gs.util.dto.SelectableNode;

import java.util.Comparator;
import java.util.Objects;

/**
 * @author Shane Merriss
 */
public class SelectableNodeComparator<T>
	implements Comparator<SelectableNode<T>> {

	public SelectableNodeComparator() {
		this(
			true,
			(dto1, dto2) -> {
				Comparable<T> comparable;

				try {
					comparable = (Comparable<T>) dto1;

					return comparable.compareTo(dto2);
				} catch (ClassCastException cce) {
				}

				try {
					comparable = (Comparable<T>) dto2;

					return comparable.compareTo(dto1);
				} catch (ClassCastException cce) {
				}

				return 0;
			}
		);
	}

	public SelectableNodeComparator(
		boolean ascending, Comparator<T> dtoComparator) {

		_asc = ascending;
		_dtoComparator = dtoComparator;
	}

	@Override
	public int compare(SelectableNode<T> n1, SelectableNode<T> n2) {
		return compareDTO(n1.getDTO(), n2.getDTO());
	}

	@Override
	public boolean equals(Object obj) {
		return false;
	}

	private int compareDTO(T dto1, T dto2) {
	    if (Objects.isNull(dto1)) {
		    return -1;
	    }

	    if (Objects.isNull(dto2)) {
		    return 1;
	    }

	    if (dto1.equals(dto2)) {
		    return 0;
	    }

	    if (_asc) {
	    	return _dtoComparator.compare(dto1, dto2);
		}
	    else {
	    	return -_dtoComparator.compare(dto1, dto2);
		}
    }

	private boolean _asc;
	private Comparator<T> _dtoComparator;

}