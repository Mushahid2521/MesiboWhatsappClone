//package com.example.mesibowhatsappclone;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.view.ActionMode;
//import androidx.appcompat.view.ActionMode.Callback;
//import androidx.appcompat.widget.Toolbar;
//import com.mesibo.api.Mesibo;
//import com.mesibo.api.Mesibo.MessageParams;
//import com.mesibo.api.Mesibo.UIHelperListner;
//import com.mesibo.api.Mesibo.UserProfile;
//import com.mesibo.messaging.MesiboMessagingFragment.FragmentListener;
//import com.mesibo.messaging.MesiboUI;
//import com.mesibo.messaging.MesiboUI.Config;
//import com.mesibo.messaging.MessagingActivityNew;
//import com.mesibo.messaging.R.id;
//import com.mesibo.messaging.R.layout;
//import com.mesibo.messaging.R.menu;
//import com.mesibo.messaging.i;
//import com.mesibo.messaging.j;
//
//public class CustomMessageActivityNew extends AppCompatActivity implements FragmentListener {
//    private Toolbar c = null;
//    com.mesibo.messaging.i a = null;
//    private UIHelperListner d = null;
//    private Config e = null;
//    private UserProfile f = null;
//    private ImageView g = null;
//    private TextView h = null;
//    private TextView i = null;
//    private String j = null;
//    private Bitmap k = null;
//    private ActionMode l = null;
//    private CustomMessageActivityNew.a m = new CustomMessageActivityNew.a((byte)0);
//    private MessageParams n = null;
//    static int b = 1;
//
//    public CustomMessageActivityNew() {
//    }
//
//    protected void onCreate(Bundle var1) {
//        super.onCreate(var1);
//        Bundle var2;
//        if ((var2 = this.getIntent().getExtras()) != null) {
//            if (!Mesibo.isReady()) {
//                this.finish();
//            } else {
//                this.d = Mesibo.getUIHelperListner();
//                this.e = MesiboUI.getConfig();
//                String var3 = var2.getString("peer");
//                long var4;
//                if ((var4 = var2.getLong("groupid")) > 0L) {
//                    this.f = Mesibo.getUserProfile(var4);
//                } else {
//                    this.f = Mesibo.getUserProfile(var3);
//                }
//
//                if (this.f == null) {
//                    this.finish();
//                } else {
//                    this.n = new MessageParams(var3, var4, 3, 0);
//                    this.setContentView(layout.activity_messaging_new);
//                    this.c = (Toolbar)this.findViewById(id.toolbar);
//                    com.mesibo.messaging.n.a(this, this.c);
//                    this.setSupportActionBar(this.c);
//                    ActionBar var6;
//                    (var6 = this.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//                    var6.setDisplayShowHomeEnabled(true);
//                    this.h = (TextView)this.findViewById(id.chat_profile_subtitle);
//                    com.mesibo.messaging.n.a(this.h, com.mesibo.messaging.c.v);
//                    this.g = (ImageView)this.findViewById(id.chat_profile_pic);
//                    if (this.g != null) {
//                        this.g.setOnClickListener(new OnClickListener() {
//                            public final void onClick(View var1) {
//                                if (null != CustomMessageActivityNew.this.j) {
//                                    CustomMessageActivityNew var10000 = CustomMessageActivityNew.this;
//                                    String var10001 = CustomMessageActivityNew.this.f.name;
//                                    com.mesibo.messaging.e.a(var10000, CustomMessageActivityNew.this.j);
//                                }
//                            }
//                        });
//                    }
//
//                    RelativeLayout var7 = (RelativeLayout)this.findViewById(id.name_tite_layout);
//                    this.i = (TextView)this.findViewById(id.chat_profile_title);
//                    this.i.setText(this.f.name);
//                    com.mesibo.messaging.n.a(this.i, com.mesibo.messaging.c.v);
//                    if (this.i != null) {
//                        var7.setOnClickListener(new OnClickListener() {
//                            public final void onClick(View var1) {
//                                if (null != CustomMessageActivityNew.this.d) {
//                                    CustomMessageActivityNew.this.d.Mesibo_onShowProfile(CustomMessageActivityNew.this, CustomMessageActivityNew.this.f);
//                                }
//
//                            }
//                        });
//                    }
//
//                    if (this.findViewById(id.fragment_container) != null && var1 == null) {
//                        this.a = new i();
//                        this.a.setArguments(this.getIntent().getExtras());
//                        this.getSupportFragmentManager().beginTransaction().add(id.fragment_container, this.a).commit();
//                    }
//                }
//            }
//        }
//    }
//
//    protected void onStart() {
//        super.onStart();
//    }
//
//    public boolean onCreateOptionsMenu(Menu var1) {
//        if (this.d == null) {
//            return true;
//        } else {
//            this.d.Mesibo_onGetMenuResourceId(this, b, this.n, var1);
//            return true;
//        }
//    }
//
//    public boolean onOptionsItemSelected(MenuItem var1) {
//        int var2;
//        if ((var2 = var1.getItemId()) == 16908332) {
//            this.finish();
//            return true;
//        } else {
//            this.d.Mesibo_onMenuItemSelected(this, b, this.n, var2);
//            return super.onOptionsItemSelected(var1);
//        }
//    }
//
//    protected void onDestroy() {
//        super.onDestroy();
//    }
//
//    public void onBackPressed() {
//        if (!this.a.Mesibo_onBackPressed()) {
//            super.onBackPressed();
//        }
//    }
//
//    public void Mesibo_onUpdateUserPicture(UserProfile var1, Bitmap var2, String var3) {
//        this.k = var2;
//        this.j = var3;
//        this.g.setImageDrawable(new j(this.k));
//    }
//
//    public void Mesibo_onUpdateUserOnlineStatus(UserProfile var1, String var2) {
//        if (var2 == null) {
//            this.h.setVisibility(View.VISIBLE);
//        } else {
//            this.h.setVisibility(View.GONE);
//            this.h.setText(var2);
//        }
//    }
//
//    public void Mesibo_onShowInContextUserInterface() {
//        this.l = this.startSupportActionMode(this.m);
//    }
//
//    public void Mesibo_onHideInContextUserInterface() {
//        this.l.finish();
//    }
//
//    public void Mesibo_onContextUserInterfaceCount(int var1) {
//        if (this.l != null) {
//            this.l.setTitle(String.valueOf(var1));
//            this.l.invalidate();
//        }
//    }
//
//    public void Mesibo_onError(int var1, String var2, String var3) {
//        com.mesibo.messaging.n.a(this, var2, var3);
//    }
//
//    public void onRequestPermissionsResult(int var1, String[] var2, int[] var3) {
//        this.a.Mesibo_onRequestPermissionsResult(var1, var2, var3);
//    }
//
//    protected void onActivityResult(int var1, int var2, @Nullable Intent var3) {
//        super.onActivityResult(var1, var2, var3);
//        this.a.Mesibo_onActivityResult(var1, var2, var3);
//    }
//
//    protected void onResume() {
//        super.onResume();
//        com.mesibo.messaging.e.a(this);
//    }
//
//    private class a implements Callback {
//        private final String b;
//
//        private a(byte b) {
//            this.b = CustomMessageActivityNew.a.class.getSimpleName();
//        }
//
//        public boolean onCreateActionMode(ActionMode var1, Menu var2) {
//            var2.clear();
//            var1.getMenuInflater().inflate(menu.selected_menu, var2);
//            var2.findItem(id.menu_reply).setShowAsAction(2);
//            var2.findItem(id.menu_star).setShowAsAction(2);
//            var2.findItem(id.menu_resend).setShowAsAction(2);
//            var2.findItem(id.menu_copy).setShowAsAction(2);
//            var2.findItem(id.menu_forward).setShowAsAction(2);
//            var2.findItem(id.menu_forward).setVisible(CustomMessageActivityNew.this.e.enableForward);
//            var2.findItem(id.menu_forward).setEnabled(CustomMessageActivityNew.this.e.enableForward);
//            var2.findItem(id.menu_remove).setShowAsAction(2);
//            return true;
//        }
//
//        public boolean onPrepareActionMode(ActionMode var1, Menu var2) {
//            int var3 = CustomMessageActivityNew.this.a.Mesibo_onGetEnabledActionItems();
//            var2.findItem(id.menu_resend).setVisible((var3 & 1) > 0);
//            var2.findItem(id.menu_copy).setVisible((var3 & 1) > 0);
//            var2.findItem(id.menu_copy).setVisible((var3 & 1) > 0);
//            return true;
//        }
//
//        public boolean onActionItemClicked(ActionMode var1, MenuItem var2) {
//            byte var3 = 0;
//            if (var2.getItemId() == id.menu_remove) {
//                var3 = 1;
//            } else if (var2.getItemId() == id.menu_copy) {
//                var3 = 1;
//            } else if (var2.getItemId() == id.menu_resend) {
//                var3 = 1;
//            } else if (var2.getItemId() == id.menu_forward) {
//                var3 = 1;
//            } else if (var2.getItemId() == id.menu_star) {
//                var3 = 1;
//            } else if (var2.getItemId() == id.menu_reply) {
//                var3 = 1;
//            }
//
//            if (var3 > 0) {
//                CustomMessageActivityNew.this.a.Mesibo_onActionItemClicked(var3);
//                var1.finish();
//                CustomMessageActivityNew.this.a.Mesibo_onInContextUserInterfaceClosed();
//                return true;
//            } else {
//                return false;
//            }
//        }
//
//        public void onDestroyActionMode(ActionMode var1) {
//            CustomMessageActivityNew.this.a.Mesibo_onInContextUserInterfaceClosed();
//            CustomMessageActivityNew.e(CustomMessageActivityNew.this);
//        }
//    }
//
//}
//
