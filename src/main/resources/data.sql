insert into role (role_name) value ('USER')
insert into role (role_name) value ('ADMIN')
insert into user (email, username,securityQuestion, securityAnswer, enabled, isApproved, password) values ('blabla@blalba.de', 'derToaster1412','Are you Stupid ?', '$2y$12$N0OWmO8eAhQRTkiU8UGrKOQjGFHqzPH.W0S2Vr.IChV/VY86hLiSC' , true, true, '$2y$12$WzEwRSwWSVCP3.LRWUvQAOMDoAKBzJ7rBPSdEMRjCa6kwt.29/bKu')
insert into user (email, username,securityQuestion, securityAnswer, enabled, isApproved, password) values ('yadeyade@blalba.de', 'Zockerlady','Are you Stupid ?','$2y$12$N0OWmO8eAhQRTkiU8UGrKOQjGFHqzPH.W0S2Vr.IChV/VY86hLiSC', true, true,  'yadeyade')
insert into user (email, username,securityQuestion, securityAnswer, enabled, isApproved, password) values ('yadeyade@blalba.de', 'user','Are you Stupid ?','$2y$12$N0OWmO8eAhQRTkiU8UGrKOQjGFHqzPH.W0S2Vr.IChV/VY86hLiSC', true, false,  '$2y$12$MlPwR/nF9ixmD1P3x.ZJNOwBIWIW.xX6ZSLPbmBmzJsD2T6WQjkaa')
insert into user (email, username,securityQuestion, securityAnswer, enabled, isApproved, password) values ('yadeyade@blalba.de', 'admin','Are you Stupid ?', '$2y$12$N0OWmO8eAhQRTkiU8UGrKOQjGFHqzPH.W0S2Vr.IChV/VY86hLiSC',true, true,  '$2y$12$3GCrY8KRT.zNT9TIBWPjsuf6ieKh8btST0Ytl9TBa5zXCMVBsqlpC')
insert into user (email, username,securityQuestion, securityAnswer, enabled, isApproved, password) values ('yadeyade@blalba.de', 'secondAdmin','Are you Stupid ?','$2y$12$N0OWmO8eAhQRTkiU8UGrKOQjGFHqzPH.W0S2Vr.IChV/VY86hLiSC', true, true,  '$2y$12$3GCrY8KRT.zNT9TIBWPjsuf6ieKh8btST0Ytl9TBa5zXCMVBsqlpC')
insert into user (email, username,securityQuestion, securityAnswer, enabled, isApproved, password) values ('yadeyade@blalba.de', 'Sörenfried','Are you Stupid ?','$2y$12$N0OWmO8eAhQRTkiU8UGrKOQjGFHqzPH.W0S2Vr.IChV/VY86hLiSC', true, true,  '$2y$12$rYK0KIoyzeXBntRRoy6Mf.Yk0.e9fkj75ZSx6GdHT5LgisJn4Ug2O')
insert into user (email, username,securityQuestion, securityAnswer, enabled, isApproved, password) values ('yadeyade@blalba.de', 'Soerschaden','Are you Stupid ?','$2y$12$N0OWmO8eAhQRTkiU8UGrKOQjGFHqzPH.W0S2Vr.IChV/VY86hLiSC', true, true,  '$2y$12$rYK0KIoyzeXBntRRoy6Mf.Yk0.e9fkj75ZSx6GdHT5LgisJn4Ug2O')
insert into user_role (user_id, role_id) value (3,1)
insert into user_role (user_id, role_id) value (4,2)
insert into user_role (user_id, role_id) value (4,1)
insert into user_role (user_id, role_id) value (5,2)
insert into skilllevels(skilllevel) values('Noob')
insert into skilllevels(skilllevel) values('Intermediate')
insert into skilllevels(skilllevel) values('Advanced')
insert into skilllevels(skilllevel) values('Pro')
insert into skilllevels(skilllevel) values('Godlike')
insert into skilllevels(skilllevel) values('M M M M M Multikill')
insert into skilllevels(skilllevel) values('Hendrix')
insert into instruments(name) values('Guitar')
insert into instruments(name) values('Flute')
insert into instruments(name) values('Drums')
insert into instruments(name) values('Keys')
insert into instruments(name) values('Vocal')
insert into instruments(name) values('Alto Sax')
insert into instruments(name) values('Tenor Sax')
insert into instruments(name) values('Soprano Sax')
insert into instruments(name) values('Trompet')
insert into instruments(name) values('Bass')
insert into instruments(name) values('Doublebass')
insert into instruments(name) values('Percussions')
insert into instruments(name) values('Banjo')
insert into instruments(name) values('Mouthorgan')
insert into instruments(name) values('Clarinet')
insert into instruments(name) values('Tuba')
insert into instruments(name) values('Trombone')
insert into skills(user_id, instrument_id, skilllevel_id) values(1, 1, 1)
insert into band(bandSize, genre,isSoeren, name, owner_user_id) values (5, 'Rock', false,  'Toaster Band', 1)
insert into band(bandSize, genre,isSoeren, name, owner_user_id) values (12, 'Jazz', false, 'Oven Band', 1)
insert into band(bandSize, genre,isSoeren, name, owner_user_id) values (6, 'Punk', false, 'Fridge Band', 1)
insert into band(bandSize, genre,isSoeren, name, owner_user_id) values (3, 'Blues', false, 'Beer Band', 1)
insert into band(bandSize, genre,isSoeren, name, owner_user_id) values (4, 'A Capella', false, 'Hipster Band', 1)
insert into band(bandSize, genre,isSoeren, name, owner_user_id) values (4, 'A Capella',true, 'The Annoyatons', 6)
insert into band(bandSize, genre,isSoeren, name, owner_user_id) values (4, 'Mouthorgan',true, 'Buzzingsound', 6)
insert into band(bandSize, genre,isSoeren, name, owner_user_id) values (4, 'Folk',true, 'Egal', 6)

