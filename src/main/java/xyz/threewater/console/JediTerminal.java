package xyz.threewater.console;


import com.jediterm.pty.PtyMain;
import com.jediterm.terminal.TabbedTerminalWidget;
import com.jediterm.terminal.TtyConnector;
import com.jediterm.terminal.ui.*;
import com.jediterm.terminal.ui.settings.DefaultTabbedSettingsProvider;
import com.jediterm.terminal.ui.settings.TabbedSettingsProvider;
import com.pty4j.PtyProcess;

import javax.annotation.Nullable;
import javax.swing.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class JediTerminal {

    private TerminalWidget myTerminal;

    /**
     * 构造函数
     */
    public JediTerminal() {
        myTerminal = createTabbedTerminalWidget();
        openSession(myTerminal);
    }

    public JComponent getSwingComponent(){
      return myTerminal.getComponent();
    }


    @Nullable
    protected JediTermWidget openSession(TerminalWidget terminal) {
        if (terminal.canOpenSession()) {
            return openSession(terminal, createTtyConnector());
        }
        return null;
    }

    public JediTermWidget openSession(TerminalWidget terminal, TtyConnector ttyConnector) {
        JediTermWidget session = terminal.createTerminalSession(ttyConnector);
        session.start();
        return session;
    }

    public TtyConnector createTtyConnector() {
        try {
            Map<String, String> envs = new HashMap<>(System.getenv());
            String[] command;

            if (UIUtil.isWindows) {
                command = new String[]{"cmd.exe"};
            } else {
                command = new String[]{"/bin/bash", "--login"};
                envs.put("TERM", "xterm");
            }

            PtyProcess process = PtyProcess.exec(command, envs, null);

            return new PtyMain.LoggingPtyProcessTtyConnector(process, Charset.forName("UTF-8"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    protected AbstractTabbedTerminalWidget createTabbedTerminalWidget() {
        return new TabbedTerminalWidget(new DefaultTabbedSettingsProvider(), this::openSession) {
            @Override
            public JediTermWidget createInnerTerminalWidget() {
                return createTerminalWidget(getSettingsProvider());
            }
        };
    }

    protected JediTermWidget createTerminalWidget(TabbedSettingsProvider settingsProvider) {
        return new JediTermWidget(settingsProvider);
    }

}
