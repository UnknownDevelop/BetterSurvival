package com.bettersurvival.util.heap;

import java.lang.reflect.Array;

public class Heap<T extends IHeapItem<T>>
{
	T[] items;
	int currentItemCount;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Heap(Class typeClass, int maxHeapSize)
    {
        items = (T[])Array.newInstance(typeClass, maxHeapSize);
    }

    public void Add(T item)
    {
        item.setHeapIndex(currentItemCount);
        items[currentItemCount] = item;
        sortUp(item);
        currentItemCount++;
    }
    
    public T removeFirst()
    {
        T firstItem = items[0];
        currentItemCount--;
        items[0] = items[currentItemCount];
        items[0].setHeapIndex(0);
        sortDown(items[0]);
        return firstItem;
    }

    public void updateItem(T item)
    {
        sortUp(item);
    }

    public int GetLength()
    {
    	return currentItemCount;
    }

    public boolean contains(T item)
    {
        return items.equals(items[item.getHeapIndex()]);
    }

    void sortDown(T item)
    {
        while(true)
        {
            int childIndexLeft = item.getHeapIndex() * 2 + 1;
            int childIndexRight = item.getHeapIndex() * 2 + 2;
            int swapIndex = 0;

            if(childIndexLeft < currentItemCount)
            {
                swapIndex = childIndexLeft;

                if(childIndexRight < currentItemCount)
                {
                    if(items[childIndexLeft].compareTo(items[childIndexRight]) < 0)
                    {
                        swapIndex = childIndexRight;
                    }
                }

                if(item.compareTo(items[swapIndex]) < 0)
                {
                    swap(item, items[swapIndex]);
                }
                else
                {
                    return;
                }
            }
            else
            {
                return;
            }
        }
    }

    void sortUp(T item)
    {
        int parentIndex = (item.getHeapIndex() - 1) / 2;

        while(true)
        {
            T parentItem = items[parentIndex];
            if(item.compareTo(parentItem) > 0)
            {
                swap(item, parentItem);
            }
            else
            {
                break;
            }

            parentIndex = (item.getHeapIndex() - 1) / 2;
        }
    }

    void swap(T itemA, T itemB)
    {
        items[itemA.getHeapIndex()] = itemB;
        items[itemB.getHeapIndex()] = itemA;
        int itemAIndex = itemA.getHeapIndex();
        itemA.setHeapIndex(itemB.getHeapIndex());
        itemB.setHeapIndex(itemAIndex);
    }
}

