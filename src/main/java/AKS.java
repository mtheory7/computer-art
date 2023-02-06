import java.math.BigInteger;

public class AKS {
    private int log;
    private boolean sieveArray[];
    private int SIEVE_ERATOS_SIZE = 100000000;

    public AKS() {
        sieveEratosInit();
    }

    public boolean checkIsPrime(BigInteger n) {
        BigInteger lowR, powOf, x, leftH, rightH, aBigNum;
        int totR, quot, tm, aCounter, aLimit;
        log = (int) logBigNum(n);
        if (findPower(n, log)) {
            return false;
        }
        lowR = new BigInteger("2");
        x = lowR;
        totR = lowR.intValue();
        for (lowR = new BigInteger("2");
             lowR.compareTo(n) < 0;
             lowR = lowR.add(BigInteger.ONE)) {
            if ((lowR.gcd(n)).compareTo(BigInteger.ONE) != 0) {
                return false;
            }
            totR = lowR.intValue();
            if (sieveArray[totR]) {
                quot = largestFactor(totR - 1);
                tm = (int) (4 * (Math.sqrt(totR)) * log);
                powOf = mPower(n, new BigInteger(
                        "" + (totR - 1) / quot), lowR);
                if (quot >= tm && (powOf.compareTo(BigInteger.ONE)) != 0) {
                    break;
                }
            }
        }
        aLimit = (int) (2 * Math.sqrt(totR) * log);
        for (aCounter = 1; aCounter < aLimit; aCounter++) {
            aBigNum = new BigInteger("" + aCounter);
            leftH = (mPower(x.subtract(aBigNum), n, n)).mod(n);
            rightH = (mPower(x, n, n).subtract(aBigNum)).mod(n);
            if (leftH.compareTo(rightH) != 0) return false;
        }
        return true;
    }

    public double logBigNum(BigInteger bNum) {
        String str = "." + bNum.toString();
        return Math.log10(Double.parseDouble(str)) + (str.length() - 1);
    }

    public int largestFactor(int num) {
        int i = num;
        if (i == 1) return i;
        while (i > 1) {
            while (sieveArray[i]) {
                i--;
            }
            if (num % i == 0) {
                return i;
            }
            i--;
        }
        return num;
    }

    public boolean findPowerOf(BigInteger bNum, int val) {
        int l;
        double len;
        BigInteger low, high, mid, res;
        low = new BigInteger("10");
        high = new BigInteger("10");
        len = (bNum.toString().length()) / val;
        l = (int) Math.ceil(len);
        low = low.pow(l - 1);
        high = high.pow(l).subtract(BigInteger.ONE);
        while (low.compareTo(high) <= 0) {
            mid = low.add(high);
            mid = mid.divide(new BigInteger("2"));
            res = mid.pow(val);
            if (res.compareTo(bNum) < 0) {
                low = mid.add(BigInteger.ONE);
            } else if (res.compareTo(bNum) > 0) {
                high = mid.subtract(BigInteger.ONE);
            } else if (res.compareTo(bNum) == 0) {
                return true;
            }
        }
        return false;
    }

    boolean findPower(BigInteger n, int l) {
        int i;
        for (i = 2; i < l; i++) {
            if (findPowerOf(n, i)) {
                return true;
            }
        }
        return false;
    }

    BigInteger mPower(BigInteger x, BigInteger y, BigInteger n) {
        BigInteger m, p, z, two;
        m = y;
        p = BigInteger.ONE;
        z = x;
        two = new BigInteger("2");
        while (m.compareTo(BigInteger.ZERO) > 0) {
            while (((m.mod(two)).compareTo(BigInteger.ZERO)) == 0) {
                m = m.divide(two);
                z = (z.multiply(z)).mod(n);
            }
            m = m.subtract(BigInteger.ONE);
            p = (p.multiply(z)).mod(n);
        }
        return p;
    }

    public void sieveEratosInit() {
        int i, j;
        sieveArray = new boolean[SIEVE_ERATOS_SIZE + 1];
        sieveArray[1] = true;
        for (i = 2; i * i <= SIEVE_ERATOS_SIZE; i++) {
            if (sieveArray[i] != true) {
                for (j = i * i; j <= SIEVE_ERATOS_SIZE; j += i) {
                    sieveArray[j] = true;
                }
            }
        }
    }
}








