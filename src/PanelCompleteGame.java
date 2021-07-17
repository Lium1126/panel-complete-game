/**
 * オブジェクト指向プログラミングおよび演習 第13回
 * 課題2
 *
 * パネルコンプリートゲーム
 *
 * @version 1.0
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * メインクラス
 */
public class PanelCompleteGame extends JFrame {

    /*---フィールド---*/
    private JPanel panel1;
    private JPanel panel3;
    private JPanel panel2;
    private JPanel panel4;
    private JPanel panel5;
    private JPanel panel6;
    private JPanel panel7;
    private JPanel panel8;
    private JPanel panel9;
    private JLabel label1;
    private JLabel label3;
    private JLabel label2;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;
    private JLabel label7;
    private JLabel label8;
    private JLabel label9;
    private JPanel parentPanel;

    private List<JPanel> panels = new ArrayList<>(Arrays.asList(panel1, panel2, panel3, panel4, panel5, panel6, panel7, panel8, panel9));
    private List<JLabel> labels = new ArrayList<>(Arrays.asList(label1, label2, label3, label4, label5, label6, label7, label8, label9));
    private List< List< Integer > > toggles = new ArrayList<>(Arrays.asList(
            new ArrayList<>(Arrays.asList(2, 4)),        // panel1
            new ArrayList<>(Arrays.asList(1, 3, 5)),     // panel2
            new ArrayList<>(Arrays.asList(2, 6)),        // panel3
            new ArrayList<>(Arrays.asList(1, 5, 7)),     // panel4
            new ArrayList<>(Arrays.asList(2, 4, 6, 8)),  // panel5
            new ArrayList<>(Arrays.asList(3, 5, 9)),     // panel6
            new ArrayList<>(Arrays.asList(4, 8)),        // panel7
            new ArrayList<>(Arrays.asList(5, 7, 9)),     // panel8
            new ArrayList<>(Arrays.asList(6, 8))         // panel9
    ));

    /*---コンストラクタ---*/
    /**
     * @param title ウィンドウタイトル
     */
    public PanelCompleteGame(String title) {

        /*---GUI設定---*/
        // ウィンドウタイトルを設定
        super.setTitle(title);

        // ウィンドウを閉じたら終了させる
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // ウィンドウ起動位置とウィンドウサイズの設定
        setBounds(100, 100, 350, 350);

        // コンテンツパネル設定
        setContentPane(parentPanel);

        /*---ゲーム設定---*/
        // パネル、ラベル、自身以外のトグルするパネルの番号の配列を生成する
        List<PanelLabelData> data = new ArrayList<>();
        for (int i = 0;i < 9;i += 1) {
            data.add(new PanelLabelData(panels.get(i), labels.get(i), toggles.get(i)));
        }

        // ラベルの初期化
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);

                initLabels();
            }
        });

        // 各パネルにクリックイベントを登録
        for (PanelLabelData datum : data) {
            datum.getPanel().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);

                    // 自身の"0"と"1"をトグル
                    toggleLabel(datum.getPanel(), datum.getLabel());

                    // 自身に隣接したパネルをトグル
                    for (int j = 0;j < datum.getToggles().size();j++) {
                        PanelLabelData target = data.get(datum.getToggles().get(j) - 1);
                        toggleLabel(target.getPanel(), target.getLabel());
                    }

                    // クリアしていたらダイアログメッセージを表示
                    if (checkClear()) {
                        showClearMessage();
                    }
                }
            });

        }
    }

    /*---メソッド---*/
    /**
     * メインメソッド
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {
        // イベントキューに画面の起動スレッドを追加
        EventQueue.invokeLater(() -> { new PanelCompleteGame("k19061").setVisible(true); });
    }

    /**
     * ラベルの"0"と"1"をトグルし、パネル背景色を変更する
     * @param panel
     * @param label
     */
    private void toggleLabel(JPanel panel, JLabel label) {
        int now_state = Integer.parseInt(label.getText());  // 現在のラベルを取得
        changePanelState(label, Integer.toString((now_state + 1) % 2), panel);  // "0"と"1"をトグル
    }

    /**
     * クリアチェックする
     * @return クリアしていたらtrueを返す
     */
    private boolean checkClear() {

        // クリア判定
        boolean clear_flag = true;
        for (JLabel label : labels) {
           if (label.getText().equals("0")) clear_flag = false;
        }

        return clear_flag;
    }

    /**
     * ゲームクリアを伝えるメッセージダイアログを表示する
     * その後、再度出題する
     */
    private void showClearMessage() {
        JOptionPane.showMessageDialog(
                this,
                "GameClear!!",       // メッセージ
                "Congratulations!!",    // タイトル
                JOptionPane.PLAIN_MESSAGE);  // ダイアログタイプ

        // 再度出題(ラベルの初期化)
        initLabels();
    }

    /**
     * 9つのラベルを初期化する
     */
    private void initLabels() {
        do {
            for (int i = 0; i < 9; i++) {
                // ラベルをランダムな"0"か"1"で初期化
                int r = new java.util.Random().nextInt(2);
                changePanelState(labels.get(i), Integer.toString(r), panels.get(i));
            }
        } while (checkClear());   // 出題時点でのゲームクリアを回避
    }

    /**
     * パネルの状態を変化させる
     * @param label 変化させるラベル
     * @param str ラベルに設定する文字列
     * @param panel 変化させるパネル
     */
    private void changePanelState(JLabel label, String str, JPanel panel) {
        label.setText(str);

        // パネルの背景色設定
        if (str.equals("0")) panel.setBackground(Color.WHITE);
        else panel.setBackground(Color.RED);
    }
}
