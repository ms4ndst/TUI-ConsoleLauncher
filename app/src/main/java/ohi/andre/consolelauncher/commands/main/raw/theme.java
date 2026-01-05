package ohi.andre.consolelauncher.commands.main.raw;

import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.Arrays;

import ohi.andre.consolelauncher.R;
import ohi.andre.consolelauncher.commands.CommandAbstraction;
import ohi.andre.consolelauncher.commands.ExecutePack;
import ohi.andre.consolelauncher.commands.main.MainPack;
import ohi.andre.consolelauncher.commands.main.specific.ParamCommand;
import ohi.andre.consolelauncher.managers.ThemeManager;
import ohi.andre.consolelauncher.managers.ThemePalette;
import ohi.andre.consolelauncher.tuils.Tuils;

/**
 * Created by francescoandreuzzi on 20/08/2017.
 */

public class theme extends ParamCommand {

    private enum Param implements ohi.andre.consolelauncher.commands.main.Param {

        apply {
            @Override
            public int[] args() {
                return new int[] {CommandAbstraction.PLAIN_TEXT};
            }

            @Override
            public String exec(ExecutePack pack) {
                String selection = pack.getString();
                if (selection == null) return null;

                selection = selection.trim();
                if (selection.length() == 0) return null;

                // If the user passes a number, treat it as a selection from the local palette (1-based)
                if (Tuils.isNumber(selection)) {
                    int oneBased = Integer.parseInt(selection);
                    int index = oneBased - 1;

                    Intent intent = new Intent(ThemeManager.ACTION_APPLY_LOCAL);
                    intent.putExtra(ThemeManager.INDEX, index);
                    LocalBroadcastManager.getInstance(pack.context.getApplicationContext()).sendBroadcast(intent);
                    return null;
                }

                // Otherwise fall back to the legacy behavior (apply online theme by id/name)
                Intent intent = new Intent(ThemeManager.ACTION_APPLY);
                intent.putExtra(ThemeManager.NAME, selection);
                LocalBroadcastManager.getInstance(pack.context.getApplicationContext()).sendBroadcast(intent);
                return null;
            }
        },
        standard {
            @Override
            public int[] args() {
                return new int[] {};
            }

            @Override
            public String exec(ExecutePack pack) {
                LocalBroadcastManager.getInstance(pack.context.getApplicationContext()).sendBroadcast(new Intent(ThemeManager.ACTION_STANDARD));
                return null;
            }
        },
        view {
            @Override
            public String exec(ExecutePack pack) {
                // List built-in color schemes by name, alphabetically, and number them (1-based)
                String[] names = ThemePalette.getSchemeNames();
                String[] numbered = new String[names.length];
                for (int i = 0; i < names.length; i++) {
                    numbered[i] = (i + 1) + ") " + names[i];
                }
                return Tuils.toPlanString(numbered, Tuils.NEWLINE);
            }
        },
        viewe {
            @Override
            public String exec(ExecutePack pack) {
                // Alias for -view: keep for backwards compatibility
                String[] names = ThemePalette.getSchemeNames();
                String[] numbered = new String[names.length];
                for (int i = 0; i < names.length; i++) {
                    numbered[i] = (i + 1) + ") " + names[i];
                }
                return Tuils.toPlanString(numbered, Tuils.NEWLINE);
            }
        },
        create {
            @Override
            public String exec(ExecutePack pack) {
                pack.context.startActivity(Tuils.webPage("https://tui.tarunshankerpandey.com/create.php"));
                return null;
            }
        },
        old {
            @Override
            public String exec(ExecutePack pack) {
                LocalBroadcastManager.getInstance(pack.context.getApplicationContext()).sendBroadcast(new Intent(ThemeManager.ACTION_REVERT));
                return null;
            }
        },
        tutorial {
            @Override
            public String exec(ExecutePack pack) {
                pack.context.startActivity(Tuils.webPage("https://github.com/Andre1299/TUI-ConsoleLauncher/wiki/Themes"));
                return null;
            }
        };

        static Param get(String p) {
            p = p.toLowerCase();
            Param[] ps = values();
            for (Param p1 : ps)
                if (p.endsWith(p1.label()))
                    return p1;
            return null;
        }

        static String[] labels() {
            Param[] ps = values();
            String[] ss = new String[ps.length];

            for (int count = 0; count < ps.length; count++) {
                ss[count] = ps[count].label();
            }

            return ss;
        }

        @Override
        public String label() {
            return Tuils.MINUS + name();
        }

        @Override
        public String onNotArgEnough(ExecutePack pack, int n) {
            return pack.context.getString(R.string.help_theme);
        }

        @Override
        public String onArgNotFound(ExecutePack pack, int index) {
            return null;
        }

        @Override
        public int[] args() {
            return new int[0];
        }
    }

    @Override
    public String[] params() {
        return Param.labels();
    };

    @Override
    protected ohi.andre.consolelauncher.commands.main.Param paramForString(MainPack pack, String param) {
        return Param.get(param);
    }

    @Override
    public int priority() {
        return 4;
    }

    @Override
    public int helpRes() {
        return R.string.help_theme;
    }

    @Override
    protected String doThings(ExecutePack pack) {
        return null;
    }
}
