import java.io.*;
import java.util.*;

public class Suudoku{
    long starttime, endtime;
    int place_num = 0; //���݈ʒu
    int count = 0; //�����񐔃J�E���^�[
    Suudoku(int[] data){ //�R���X�g���N�^
	starttime = System.nanoTime();
	if (solve(place_num, data)){
	    endtime = System.nanoTime();
	    printAnswer(data);
	    printCount();
	}
    }
    public static void main(String[] args){
	int[] data = new int[81];
	if(args.length!=1){ //���q�s��
	    System.err.println("need 1 argument");
	    System.exit(1);
	}
	BufferedReader br;
	try{ //�t�@�C���ǂݍ���
	    br = new BufferedReader(new FileReader(args[0]));
	    
	    for(int i=0; i<9; i++){
		String tmp[] = br.readLine().split(" ");
		for(int j=0; j<9; j++){
		    data[i*9+j]=Integer.parseInt(tmp[j]);
		    //�f�[�^����s���ǂݍ��݁E������f�[�^���󔒂ŋ�؂�
		    //int�^��81�̃f�[�^�ɕϊ�
		}
	    }
	}catch(Exception e){//�t�@�C���ǂݍ��݃G���[
	    System.err.println(""+e);
	    System.exit(1);
	}
	for (int u = 0; u < 9; u++) {//����\��
	    for (int v = 0; v < 9; v++) {
		System.out.print(data[u * 9 + v] + " ");
	    }
	    System.out.println();
	}
	new Suudoku(data); //�R���X�g���N�^�Ăяo��
    }
    private boolean solve(int k, int[] data) {//�e�}�X�̐���������
	if (k == 81) { //�S�Ẵ}�X�����܂��Ă��邱�Ƃ��m�F����ture
	    return true;
	} else if (data[k] != 0) { //data���Œ肳��Ă����玟�̃}�X��
	    return solve(k + 1, data);
	} else { //�Œ肳��ĂȂ������ꍇ
	    Integer[] randoms = createRandomNumbers();
	    //1~9�̐������d���Ȃ������_������
	    for (int i = 0; i < 9; i++) { //���E�c�E3�~3�̐����d���`�F�b�N
		if (!checkAcross(k, randoms[i], data) &&
		    !checkVertical(k, randoms[i], data) &&
		    !checkBox(k, randoms[i], data)) {
		    data[k] = randoms[i]; //����������
		    count++;
		    if (solve(k + 1, data)){ //���̃}�X�̐^�U���`�F�b�N
			return true;
		    } else { //���̃}�X�������ł��Ȃ������猻�݃}�X��������
			data[k] = 0;
		    }
		}
	    }
	}
	return false;
    }
    private boolean checkAcross(int place, int num, int[] data) { //��
	for (int i = 0; i <= 72; i += 9) {
	    if (place >= i && place <= i + 8) {
		for (int j = i; j <= i + 8; j++) {
		    if (data[j] == num) {
			return true;
		    }
		}
	    }
	} return false;
    }
    private boolean checkVertical(int p, int num, int[] data) { //�c
	for (int i = 0; i <= 8; i++) {
	    if (p == i || p == i + 9 || p == i + 18 || p == i + 27 ||
		p == i + 36 || p == i + 45 || p == i + 54 ||
		p == i + 63 || p == i + 72) {
		if (data[i] == num || data[i + 9] == num ||
		    data[i + 18] == num || data[i + 27] == num ||
		    data[i + 36] == num || data[i + 45] == num ||
		    data[i + 54] == num || data[i + 63] == num ||
		    data[i + 72] == num) {
		    return true;
		}
	    }
	} return false;
    }
    private boolean checkBox(int place, int num, int[] data) { //3�~3
	for (int i = 0; i <= 54; i += 27) {
	    if (i == 0) {
		for (int j = 0; j <= 6; j += 3) {
		    if ((place >= j && place <= j + 2) ||
			(place >= j + 9 && place <= j + 11) ||
			(place >= j + 18 && place <= j + 20)) {
			if (data[j] == num || data[j + 1] == num ||
			    data[j + 2] == num || data[j + 9] == num ||
			    data[j + 10] == num || data[j + 11] == num ||
			    data[j + 18] == num || data[j + 19] == num ||
			    data[j + 20] == num) {
			    return true;
			}
		    }
		}
	    } else if (i == 27) {
		for(int s = 27; s <= 33; s += 3) {
		    if ((place >= s && place <= s + 2) ||
			(place >= s + 9 && place <= s + 11) ||
			(place >= s + 18 && place <= s + 20)) {
			if (data[s] == num || data[s + 1] == num ||
			    data[s + 2] == num || data[s + 9] == num ||
			    data[s + 10] == num || data[s + 11] == num ||
			    data[s + 18] == num || data[s + 19] == num ||
			    data[s + 20] == num) {
			    return true;
			}
		    }
		}
	    } else if (i == 54) {
		for(int t = 54; t <= 60; t += 3) {
		    if ((place >= t && place <= t + 2) ||
			(place >= t + 9 && place <= t + 11) ||
			(place >= t + 18 && place <= t + 20)) {
			if (data[t] == num || data[t + 1] == num ||
			    data[t + 2] == num || data[t + 9] == num ||
			    data[t + 10] == num || data[t + 11] == num ||
			    data[t + 18] == num || data[t + 19] == num ||
			    data[t + 20] == num) {
			    return true;
			}
		    }
		}
	    }
	} return false;
    }
    private Integer[] createRandomNumbers() { //�d�����Ȃ�������9����
	ArrayList<Integer> randoms = new ArrayList<Integer>();
	for(int i = 0; i < 9; i++) 
	    randoms.add(i + 1);
	    Collections.shuffle(randoms);
	    return randoms.toArray(new Integer[9]);
    }
    public void printAnswer(int[] data) { //���ʂ�\��
	System.out.println("---------------------------------------");
        for (int i = 0; i < 9; i++) {
	    for (int j = 0; j < 9; j++) {
		System.out.print(data[i * 9 + j] + " ");
	    }
	    System.out.println();
	}			 
    }
    public void printCount() { //�����񐔕\��
	long time = endtime - starttime;
	System.out.println();
	System.out.println("�����񐔁F" + count);
	System.out.println("�������ԁF" + time);
    }
}
