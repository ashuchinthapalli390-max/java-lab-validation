public final class BinarySearch {
 public static int search(int[] a,int key){int low=0,high=a.length-1;while(low<=high){int mid=low+(high-low)/2;if(a[mid]==key)return mid;if(a[mid]<key)low=mid+1;else high=mid-1;}return -1;}
 public static void main(String[] args){int[] a={1,2,3,4,5,6,7,8,9,10};int key=args.length==0?5:Integer.parseInt(args[0]);System.out.println("Searching for element: "+key);System.out.println(search(a,key));}
}
