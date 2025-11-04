module Game{

    struct Pixel{
        bool isMine;
    }

    sequence<Pixel> PixelArray;
    sequence<PixelArray> PixelMatrix;


    interface GameServices{
        bool selectCell(int i, int j);
        PixelMatrix getBoard();
    }
}