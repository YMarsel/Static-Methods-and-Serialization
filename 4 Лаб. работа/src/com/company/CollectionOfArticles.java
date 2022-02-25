package com.company;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class CollectionOfArticles implements Library, Serializable
{
    private int[] ArrayOfPagesInArticle;
    private String CollectionOfArticlesName;
    private int MaximalPagesOfAnnotationCount;

    CollectionOfArticles(){}
    CollectionOfArticles(int[] CountOfPages, String CollectionName, int MaxPagesOfAnnot)
    {
        this.ArrayOfPagesInArticle = CountOfPages;
        this.CollectionOfArticlesName = CollectionName;
        this.MaximalPagesOfAnnotationCount = MaxPagesOfAnnot;
    }

    @Override
    public void SetPagesArray(int[] array)
    {
        this.ArrayOfPagesInArticle = array;
    }

    @Override
    public int[] GetPagesArray()
    {
        return ArrayOfPagesInArticle;
    }

    @Override
    public String GetName()
    {
        return CollectionOfArticlesName;
    }

    @Override
    public void SetName(String name) throws ValidationException {
        if (name.isEmpty())
            throw new ValidationException("Имя не может быть пустым!", new NullPointerException());
        if (name.length() > 100)
            throw new ValidationException("Имя не может быть длинее 100 символов!", new IllegalArgumentException());
        this.CollectionOfArticlesName = name;
    }

    @Override
    public int GetPageCount (int pageNumber) throws OutOfBounds
    {
        if (pageNumber > ArrayOfPagesInArticle.length || pageNumber < 0)
            throw new OutOfBounds("Выход за пределы массива", new RuntimeException());
        return ArrayOfPagesInArticle[pageNumber];
    }
    @Override
    public void SetPageCount(int pageNumber, int value) throws OutOfBounds
    {
        if (value<0)
            throw new OutOfBounds("Не существует страницы с отрицательным номером!", new RuntimeException());
        if (pageNumber > ArrayOfPagesInArticle.length || pageNumber < 0)
            throw new OutOfBounds("Выход за пределы массива", new RuntimeException());
        this.ArrayOfPagesInArticle[pageNumber] = value;
    }

    @Override
    public int QuotedPages()
    {
        int countOfPages=0;
        int countOfAnnotationPages;
        for (int i=0;i<ArrayOfPagesInArticle.length;i++)
        {
            countOfPages += ArrayOfPagesInArticle[i];
        }
        countOfAnnotationPages = ArrayOfPagesInArticle.length * MaximalPagesOfAnnotationCount;
        return countOfPages -= countOfAnnotationPages;
    }

    @Override
    public void SetCountOfUnquotedPages(int count)
    {
        this.MaximalPagesOfAnnotationCount = count;
    }

    @Override
    public void Output(OutputStream out) throws IOException
    {
        InputStream input = new ByteArrayInputStream(this.toString().getBytes(StandardCharsets.UTF_8));
        try
        {
            byte[] buffer = new byte[65536]; // 64Kb
            while (input.available() > 0)
            {
                int real = input.read(buffer);
                out.write(buffer, 0, real);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void Write(Writer out) throws IOException
    {
        try
        {
        out.write(this.toString());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int GetCountOfUnquotedPages()
    {
        return MaximalPagesOfAnnotationCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CollectionOfArticles other = (CollectionOfArticles) obj;
        if (ArrayOfPagesInArticle != other.ArrayOfPagesInArticle)
            return false;
        if (CollectionOfArticlesName != other.CollectionOfArticlesName)
            return false;
        if (MaximalPagesOfAnnotationCount != other.MaximalPagesOfAnnotationCount)
            return false;
        return true;
    }

    @Override
    public boolean SameType(Object obj)
    {
        if (obj instanceof CollectionOfArticles)
            return true;
        else
            return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((CollectionOfArticlesName == null) ? 0 : CollectionOfArticlesName.hashCode());
        result = prime * result + ((ArrayOfPagesInArticle == null) ? 0 : Arrays.hashCode(ArrayOfPagesInArticle));
        result = prime * result + ((MaximalPagesOfAnnotationCount == 0) ? 0 : MaximalPagesOfAnnotationCount);
        return result;
    }

    @Override
    public String toString()
    {
        //CollectionOfArticles|Name|Int|Array
        return "CollectionOfArticles|" +                //Тип
                CollectionOfArticlesName + '|' +        //Имя
                MaximalPagesOfAnnotationCount + '|' +   //Кол-во незначащих страниц
                Arrays.toString(ArrayOfPagesInArticle); //Массив с кол-вом страниц
    }
}
