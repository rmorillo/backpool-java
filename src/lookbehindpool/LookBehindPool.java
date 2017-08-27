package lookbehindpool;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.*;

public class LookBehindPool<T> {
    private long _sequence = 0;
    private int _capacity = 0;
    private boolean _hasRolledOver = false;

    protected List<T> _items = null;
    protected int _currentPosition = 0;
    protected int _lastPosition = -1;
    protected int _length = 0;
    protected int _offset = 1;

    public LookBehindPool(int capacity, Function<T, T> initItem)
    {
        this._capacity = capacity;

        _items = new ArrayList<T>();

        for(int i=0; i<capacity; i++)
        {
            _items.add(initItem.apply(null));
        }
    }

    public void update(Function<T, T> updateItem)
    {
        updateItem.apply(_items.get(_currentPosition));
        moveForward();
    }

    public void moveForward()
    {
        _lastPosition = _currentPosition;
        _sequence++;

        if (!_hasRolledOver)
        {
            _length++;
        }

        if (_currentPosition < (_capacity - 1))
        {
            _currentPosition += _offset;
        }
        else
        {
            _currentPosition = 0;
            _hasRolledOver = true;
        }
    }

    private int getAbsoluteIndex(int relativeIndex)
    {
        int targetIndex = _lastPosition - relativeIndex;

        if (targetIndex < 0)
        {
            int absoluteIndex = targetIndex + _capacity;

            if (_hasRolledOver && absoluteIndex > _lastPosition)
            {
                targetIndex = absoluteIndex;
            }
            else
                throw new IllegalArgumentException();
                //throw new IndexOutOfRangeException(string.Format(Resources.LookBehindPool_GetAbsoluteIndex_PoolIndexOutOfRange, _Length - 1));
        }

        return targetIndex;
    }

    public int capacity() {
        return _capacity;
    }
}
