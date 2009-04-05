/**
 * Common JS for Trade Pages
 *
 * created: 2008-12-21  yubo
 */
var TB = YAHOO.namespace('TB');
YAHOO.namespace('TB.app.Trade');

YAHOO.lang.augmentObject(TB.app.Trade, function() {
    var Y = YAHOO.util, Dom = Y.Dom, Event = Y.Event;
    var Trade = TB.app.Trade;

    return {
        /**
         * 列表的checkbox选择框
         *
         * 约定：全选 all-selector, 普通 selector
         *
         * @param containerId
         */
        ListSelector: function(containerId) {

            var obj = {
                checkCount: 0,

                selectors: [],

                allSelectors: [],

                MAX_COUNT: 0,

                ALL_COUNT: 0,

                init: function(containerId) {
                    this.selectors = Dom.getElementsByClassName('selector', 'input', containerId);
                    this.allSelectors = Dom.getElementsByClassName('all-selector', 'input', containerId);
                    this.MAX_COUNT = this.selectors.length;
                    this.ALL_COUNT = this.allSelectors.length;

                    this.initAllSelectors();
                    this.initSelectors();
                },

                /**
                 * 初始化全选checkbox
                 */
                initAllSelectors: function() {
                    Dom.batch(this.allSelectors, function(el) {
                        Event.on(el, 'click', function() {

                            var isChecked = this.checked;
                            obj.checkedCount = isChecked ? obj.MAX_COUNT : 0;

                            for (var i = 0; i < obj.MAX_COUNT; ++i) {
                                obj.selectors[i].checked = isChecked;
                            }

                            for (i = 0; i < obj.ALL_COUNT; ++i) {
                                obj.allSelectors[i].checked = isChecked;
                            }
                        });
                    });
                },

                /**
                 * 当uncheck单个checkbox时，要uncheck全选框
                 * 当checkbox都选中时，要自动check全选框
                 */
                initSelectors: function() {
                    Dom.batch(this.selectors, function(el) {
                        Event.on(el, 'click', function() {
                            if (!el.checked) {
                                for (var i = 0; i < obj.ALL_COUNT; ++i) {
                                    obj.allSelectors[i].checked = false;
                                }
                                obj.checkedCount--;
                            } else {
                                obj.checkedCount++;
                                if (obj.checkedCount == obj.MAX_COUNT) {
                                    for (i = 0; i < obj.ALL_COUNT; ++i) {
                                        obj.allSelectors[i].checked = true;
                                    }
                                }
                            }
                        });
                    });
                }
            };

            obj.init(containerId);
            return obj;
        },

        /**
		 * 对话框管理类
		 */
		DialogMgr: {

			currentDialog: null,

            currentTrigger: null,

			initTriggers: function(container) {
				var triggers = Dom.getElementsByClassName('J_DialogTrigger', '*', container || document.body);

                var obj = this;
				for(var i = 0, len = triggers.length; i < len; ++i) {
					Event.on(triggers[i], 'click', function(ev) {
						Event.preventDefault(ev);

						var url = this.href;
						var config = Trade.Util.parseQueryString(this.getAttribute('data'));
                        obj.currentTrigger = this;
						Trade.DialogMgr.showDialog(url, config);
					});
				}
			},

            /**
             * 给对话框的关闭按钮添加事件
             */
            initDialogCloseBtn: function() {
                Event.on('J_ContainerClose', 'click', function(){
                   parent.TB.app.Trade.DialogMgr.hideDialog();
                });
            },

			initDialog: function(config) {
				// config
				config = config || {};
				config['width'] = config['width'] || '690px';
				config['height'] = config['height'] || '280px';
				config['visible'] = config['visible'] || false;
				config['modal'] = config['modal'] !== false;
				config['draggable'] = config['draggable'] || false;

				config['close'] = config['close'] !== false;

				// check exsits ?
				var dialogId = 'J_Dialog';
				var layer = Dom.get(dialogId);
                //if(layer) return;

                // create mackup
                layer = document.createElement('div');
                layer.id = dialogId;
                Dom.addClass(layer, 'dialog');

                var iframe = document.createElement('iframe');
                iframe.name = 'dialogFrame';
                iframe.style.width = (parseInt(config.width) - 20) + 'px';
                iframe.style.height = (parseInt(config.height) - 20) + 'px';
                iframe.setAttribute('frameBorder', '0');
                iframe.setAttribute('scrolling', 'no');
                iframe.src = 'about: blank';

                // wrap wrap
                var bd = document.createElement('div');
                Dom.addClass(bd, 'bd');
                bd.appendChild(iframe);

                layer.appendChild(bd);
                document.body.appendChild(layer);

                // append to document
                document.body.appendChild(layer);

                // init currentDialog
                this.currentDialog = new YAHOO.widget.Panel(layer, config);
                this.currentDialog.render();
                Dom.addClass(document.body, 'yui-skin-sam');

                var closeBtn = Dom.getElementsByClassName('container-close', 'a', layer);
                if (closeBtn.length == 1) {
                    closeBtn = closeBtn[0];
                    // 清除其它事件
                    Event.purgeElement(closeBtn);

                    // 添加新事件
                    Event.on(closeBtn, 'click', function(ev) {
                        Event.preventDefault(ev);
                        this.hideDialog();
                    }, this, true);
                }
			},

			showDialog: function(url, config) {

				if(!this.currentDialog) {
					this.initDialog(config);
				}

				// 设置url
				if(url.indexOf('?') == -1) url += '?';
				url += '&t=' + (new Date()).getTime();

				Dom.get('J_Dialog').getElementsByTagName('iframe')[0].src = url;

                // adjust
                this.currentDialog.center();

                // show it
				this.currentDialog.show();
			},

			hideDialog: function(confirmClose) {

				if(typeof confirmClose == 'undefined') {
					confirmClose = false; // 默认直接关闭
				}

				if(confirmClose) {
					var ok = confirm('确认关闭窗口么？');
					if(!ok) return false;
				}

				Dom.get('J_Dialog').getElementsByTagName('iframe')[0].src = 'about:blank';
				this.currentDialog.hide();
                // 页面中有多种不同尺寸的弹出框，因此需要重建
                var dialog = Dom.get('J_Dialog_c');
                var dialogMask = Dom.get('J_Dialog_mask');
                var resizeMonitor = Dom.get('_yuiResizeMonitor');
                dialog.parentNode.removeChild(dialog);
                dialogMask.parentNode.removeChild(dialogMask);
                resizeMonitor.parentNode.removeChild(resizeMonitor);
                this.currentDialog = null;
			}
		},

        /**
         * 改价、关闭交易、延时
         */
        TradeOperations: {

            ModifyPrice: function(form) {
                form = Dom.get(form);
                if(!form) return;

                var originalPrice = parseFloat(Dom.get('J_OriginalPrice').innerHTML);
                var postFeeEl = Dom.get('J_PostFee');
                var adjustFeeEl = Dom.get('J_AdjustFee');
                var totalPriceEl = Dom.get('J_TotalPrice');
                var errorMsg = Dom.get('J_ErrorMsg');
                var adjustFeeInputs = form.elements['adjustFee'];
                var postFeeInput = form.elements['postFee'];

                var hasDiscount = !!form.elements['discount'];
                if(hasDiscount) {
                    var discountData = form.elements['discount'];
                    //var discountCondition = discountData.getAttribute('data-condition');
                    var discountValue = discountData.getAttribute('data-value');
                    //var discountEl = Dom.get('J_Discount');
                }

                var frmInputs = [];
                if (!adjustFeeInputs.length) {
                    adjustFeeInputs = [adjustFeeInputs];
                }
                for (var i = 0, len = adjustFeeInputs.length; i < len; ++i) {
                    frmInputs.push(adjustFeeInputs[i]);
                }
                frmInputs.push(postFeeInput);

                Event.on(frmInputs, 'blur', function(){

                    // 校验
                    if(!validateInputs()) return;

                    // 更新计算公式
                    try {
                        // 更新运费
                        var postFee = parseFloat(postFeeInput.value).toFixed(2);
                        postFeeEl.innerHTML = postFee;

                        // 更新涨价或折扣
                        var totalAdjustFee = 0;
                        for (var i = 0, len = adjustFeeInputs.length; i < len; ++i) {
                            var val = YAHOO.lang.trim(adjustFeeInputs[i].value);
                            totalAdjustFee += parseFloat(val);
                        }
                        var totalAdjustFeeStr = '';
                        if(totalAdjustFee < 0) {
                            totalAdjustFeeStr = '- ' + Math.abs(totalAdjustFee).toFixed(2);
                        } else {
                            totalAdjustFeeStr = '+ ' + Math.abs(totalAdjustFee).toFixed(2);
                        }
                        adjustFeeEl.innerHTML = totalAdjustFeeStr;

                        // 判断是否满足满就减
                        var price = parseFloat(originalPrice) + parseFloat(postFee) + parseFloat(totalAdjustFee);
                        //根据UC，简单化处理，只要下单时满足就可以
                        if (hasDiscount) {
                            //if (price >= discountCondition) {
                                //Dom.removeClass(discountEl, 'hidden');
                                price -= discountValue;
                            //} else {
                            //    Dom.addClass(discountEl, 'hidden');
                            //}
                        }

                        // 更新买家实付
                        if(price <= 0) {
                            Dom.removeClass(errorMsg, 'hidden');
                            // 2009-01-15 出错了也要更新总价，保证公式始终正确
                            //return;
                        }
                        else {
                            // 说明一切ok，去除出错提示
                            Dom.addClass(errorMsg, 'hidden');
                        }
                        totalPriceEl.innerHTML = price.toFixed(2);
                        
                    } catch(e) {
                        // 只要有出错，就显示出错提示
                        Dom.removeClass(errorMsg, 'hidden');
                    }
                });

                Event.on(form, 'submit', function(ev) {
                    if(!Dom.hasClass(errorMsg, 'hidden')) {
                        Event.preventDefault(ev);
                        //alert('还有输入错误呢，请仔细检查。');
                        Trade.Util.twinkleErrorBox(errorMsg);
                    }
                });

                Event.on(frmInputs, 'input', checkFraction);
                Event.on(frmInputs, 'propertychange', checkFraction);

                /**
                 * 输入框只能输入两位小数
                 */
                function checkFraction() {
                    var val = String(this.value);
                    var p = val.lastIndexOf('.');
                    if(p < 0 || val.length < p + 1) return;

                    var frac = val.substring(p + 1);
                    if(frac.length > 2) {
                        this.value = val.substring(0, p) + '.' + frac.substring(0, 2);
                    }
                }

                /**
                 * 校验输入值是否正确
                 */
                function validateInputs() {
                    for(var i = 0, len = frmInputs.length; i < len; ++i) {
                        var input = frmInputs[i];
                        var val = YAHOO.lang.trim(input.value);

                        // 留空默认为0
                        if (val == '') {
                            input.value = '0';
                            val = 0;
                        }

                        // 非数值，报错
                        if (parseFloat(val) != val) {
                            Dom.removeClass(errorMsg, 'hidden');
                            input.focus();
                            return false;
                        }
                    }

                    // 运费必须为正数
                    var postFeeValue = YAHOO.lang.trim(postFeeInput.value);
                    if(parseFloat(postFeeValue) < 0) {
                        Dom.removeClass(errorMsg, 'hidden');
                        postFeeInput.focus();
                        return false;
                    }

                    // 都通过
                    return true;
                }
            },

            CancelOrder: function(form) {
                form = Dom.get(form);
                if(!form) return;

                Event.on(form, 'submit', function(ev) {
                   if(Dom.get('J_CloseReason').selectedIndex == 0) {
                       Event.preventDefault(ev);
                       Dom.removeClass('J_ErrorMsg', 'hidden');
                       //alert('还有错误呢，请仔细检查。');
                       Trade.Util.twinkleErrorBox('J_ErrorMsg');
                   }
                });
            }
        },

        Util: {
            /**
             * 切换面板的显示
             *
             * @param trigger
             * @param panel
             * @param callback 回调函数
             */
            togglePanel: function(trigger, panel, callback) {
                trigger = Dom.get(trigger);
                panel = Dom.get(panel);
                if (!trigger || !panel) return;

                Event.on(trigger, 'click', function(e) {
                    Event.preventDefault(e);
                    //TODO: 动画效果
                    var isVisible = (panel.style.display != 'none');
                    panel.style.display = isVisible ? 'none' : 'block';

                    if (callback) callback(trigger, panel);
                });
            },

            /**
             * 切换订单的显示信息
             */
            toggleOrderDetail: function(listTableId) {
                var orders = Dom.getElementsByClassName('hidden-detail', 'tbody', listTableId);

                for(var i = 0, len = orders.length; i < len; ++i) {
                    (function() {
                        var order = orders[i];
                        var orderHd = Dom.getElementsByClassName('order-hd', 'tr', order);

                        Event.on(orderHd, 'click', function(e) {
                            var clickTag = Event.getTarget(e).tagName;
                            if(clickTag == 'INPUT' || clickTag == 'A') return; // 点击在checkbox或链接上
                            
                            if (Dom.hasClass(order, 'hidden-detail')) {
                                Dom.replaceClass(order, 'hidden-detail', 'display-detail');
                            } else {
                                Dom.replaceClass(order, 'display-detail', 'hidden-detail');
                            }
                        });
                    })();
                }
            },

            /**
             * 悬浮显示用户信息
             */
            initUserInfoPopup: function(trigger) {
                if(!trigger || !trigger.getAttribute('data')) return;

                // 去掉新留言的alt，否则ie下会悬浮出来
                var img = trigger.getElementsByTagName('img');
                if(img && img.length > 0) {
                    img[0].setAttribute('alt', '');
                }

                var infoBox = document.createElement('div');
                Dom.addClass(infoBox, 'popup-info-box');

                var htmlCode = '<div class="bl"><div class="br">';
                htmlCode += '<div class="bd user-info">';
                htmlCode += '正在加载……';
                htmlCode += '</div>';
                htmlCode += '</div></div>';
                htmlCode += '<div class="bt"><div class="corner bt-l"></div><div class="mid"></div><div class="corner bt-r"></div></div>';
                infoBox.innerHTML = htmlCode;
                document.body.appendChild(infoBox);

                TB.widget.SimplePopup.decorate(trigger, infoBox, {
                    offset: [0, -10],
                    position: 'right',
                    effect: 'fade',
                    disableClick: false,
                    onShow: function() {
                        Dom.setStyle(infoBox, 'display', 'block');

                        // 如果在infoBox里能找到h5标签，说明信息已经成功获取，直接显示即可
                        if (infoBox.getElementsByTagName('h5').length > 0) return;

                        var infoBoxBd = Dom.getElementsByClassName('user-info', 'div', infoBox)[0];
                        var url = trigger.getAttribute('data');
                        YAHOO.util.Connect.asyncRequest('GET', url, {
                            success: function(o) {
                                try {
                                    var userInfo = eval('(' + o.responseText + ')');

                                    var htmlCode = '<h5>联系信息</h5>';
                                    htmlCode += '<table class="user-info-table"><tbody>';
                                    htmlCode += '<tr><th>姓名：</th><td>' + userInfo.name + '</td></tr>';
                                    htmlCode += '<tr><th>电话：</th><td>' + userInfo.tel + '</td></tr>';
                                    htmlCode += '<tr><th>手机：</th><td>' + userInfo.mobile + '</td></tr>';
                                    htmlCode += '<tr><th>备用电话：</th><td>' + userInfo.otherPhone + '</td></tr>';
                                    htmlCode += '<tr><th>电子邮件：</th><td>' + userInfo.email + '</td></tr>';
                                    htmlCode += '<tr><th>城市：</th><td>' + userInfo.city + '</td></tr>';
                                    htmlCode += '<tr class="sep"><td colspan="2"><hr></td></tr>';

                                    // 买家留言。没有留言时不显示
                                    if(userInfo.message) {
                                        htmlCode += '<tr><th>买家留言：</th><td class="message">' + userInfo.message + '</td></tr>';
                                        htmlCode += '<tr class="sep"><td colspan="2"><hr></td></tr>';
                                    }

                                    // 附加信息
                                    var attachCode = '';
                                    if (userInfo.attachInfo) {
                                        for (key in userInfo.attachInfo)
                                            attachCode += '<tr><th>' + key + '：</th><td>' + userInfo.attachInfo[key] + '</td></tr>';
                                    }
                                    if (attachCode.length > 0) {
                                        htmlCode += attachCode;
                                    }
                                    htmlCode += '</tbody></table>';

                                    // 查看收货地址
                                    var linkCode = '';
                                    if (userInfo.buyerAddress && userInfo.buyerAddress.length > 0) {
                                        linkCode += '<a href="' + userInfo.buyerAddress + '" target="_blank">查看收货地址</a>';
                                    }
                                    if (linkCode.length > 0) {
                                        htmlCode += '<div class="view-address">' + linkCode + '</div>';
                                    }

                                    infoBoxBd.innerHTML = htmlCode;
                                }
                                catch (e) {
                                    infoBoxBd.innerHTML = '对不起，暂时无法获得数据，请稍候重试。';
                                }
                            },
                            failure: function() {
                                infoBoxBd.innerHTML = '对不起，暂时无法获得数据，请稍候重试。';
                            }
                        });
                    }
                });
            },

            /**
             * 消保卡弹出层
             * @param trigger
             */
            initXBCardPopup: function() {
                var popup = document.createElement('div');
                popup.id = 'XBCardPopup';
                popup.setAttribute('style', 'background: #fff;width: 602px; display: none; z-index: 999999');
                popup.innerHTML = '<iframe id="XBCardFrame" src="about:blank" width="602" height="302" frameborder="0" scrolling="no"></iframe>';
                document.body.appendChild(popup);

                var triggers = Dom.getElementsByClassName('J_XBCard', 'a', 'J_ListTable');
                TB.widget.SimplePopup.decorate(triggers, popup.id, {
                    width: 602,
                    height: 402,
                    position: 'bottom',
                    eventType: 'click',
                    offset: [-500, -100],
                    onShow: function() {
                        Dom.get('XBCardFrame').src = this.trigger.href;
                    }
                });
            },

            /**
             * 闪烁box的边框
             * @param boxId
             * @param duration 闪烁持续时间 单位为 s
             */
            twinkleErrorBox: function(boxId, duration) {
                var box = Dom.get(boxId);
                box = Dom.getFirstChild(box); // <div class="msg"><p class="error"> ...
                if(!box) return;

                duration = duration || 2; // 默认为2s

                var hasBorder = true;
                var timer = YAHOO.lang.later(200, null, function() {
                    Dom.setStyle(box, 'borderWidth', hasBorder ? '0' : '1px');
                    //console.log(Dom.getStyle(box, 'borderWidth'));
                    hasBorder = !hasBorder;
                }, null, true);

                YAHOO.lang.later(duration * 1000, null, function() {
                    Dom.setStyle(box, 'borderWidth', '1px');
                    timer.cancel();
                });
            },

            /**
             * 解析QueryString为对象
             * @param string
             * @param overwrite
             * @param separator
             */
            parseQueryString: function(string, overwrite, separator) {
				if(!string || !string.length) return {};

				var obj = { };
				var pairs = string.split(separator || '&');

				var pair, name, value;
				for(var i = 0, len = pairs.length; i < len; ++i) {
					pair = pairs[i].split('=');
					name = decodeURIComponent(pair[0]);
					value = decodeURIComponent(pair[1]);
					if(value === '' || value === 'undefined') value = undefined; // &k 和 &k= 都还原成 undefined

					if(overwrite === true) {
						obj[name] = value;
					} else {
						if(typeof obj[name] == 'undefined') {
							obj[name] = value;
						} else if(typeof obj[name] == 'string') {
							obj[name] = [obj[name]];
							obj[name].push(value);
						} else {
							obj[name].push(value);
						}
					}
				}
				return obj;
			}
        },

        /**
         * 页面初始化
         *
         * @param pageId 页面id，放在body上
         */
        PageInit: function(pageId) {

            // 宝贝列表页面
            if (pageId == 'list-sold-items' || pageId == 'list-bought-items') {
                initTradeSearchBox();
                new Trade.ListSelector('J_ListTable');
                Trade.Util.toggleOrderDetail('J_ListTable');

                // 用户悬浮信息
                Dom.getElementsByClassName('J_UserInfo', 'span', 'J_ListTable', function(trigger) {
                    Trade.Util.initUserInfoPopup(trigger);
                });
            }

            // 已买到的宝贝
            if (pageId == 'list-bought-items') {
                // 消保卡
                Trade.Util.initXBCardPopup();
            }

            // 已卖出的宝贝
            if (pageId == 'list-sold-items') {
                // 批量导出面板的展开/折叠
                Trade.Util.togglePanel('J_BatchExportBtn', 'J_BatchExportPanel');
                Trade.Util.togglePanel('J_BatchExportPanelCloseBtn', 'J_BatchExportPanel');
            }

            // 订单详情页面
            if(pageId == 'trade-order-detail') {
                var stab = TB.widget.SimpleTab.decorate('J_TabView',
                    { eventType: 'mouse',
                      currentClass: 'current',
                      tabPanelClass: 'info-box'
                    });
                
                // 根据hash切换到对应的tab
                var hash = location.hash;
                if(hash && hash.length == 5) {
                    var tabIndex = hash.substring(4);
                    stab.switchTab(tabIndex);
                }
            }

            // 修改价格、延长超时时间和取消订单的触发页面
            if (pageId == 'list-sold-items' || pageId == 'list-bought-items' || pageId == 'trade-order-detail') {
                var triggers = Dom.getElementsByClassName('J_DialogTrigger', '*', document.body);
                if(triggers.length == 0) return; // 没有弹出窗口

                var loader = new Y.YUILoader({
                   require: ['container'],
                   base: 'http://assets.taobaocdn.com/yui/2.6.0/build/',
                   onSuccess: function() {
                       Trade.DialogMgr.initTriggers();
                   },
                   onFailure: function(o) {
                       alert("error: " + YAHOO.lang.dump(o)); 
                   }
                });

                loader.insert();
            }

            // 修改价格弹出页面
            if (pageId == 'modify-price') {
                Trade.TradeOperations.ModifyPrice(document.forms['modifyPriceForm']);
            }

            // 取消订单弹出页面
            if (pageId == 'cancel-order') {
                Trade.TradeOperations.CancelOrder(document.forms['cancelOrderForm']);
            }

            if (pageId == 'modify-price' || pageId == 'cancel-order'
                    || pageId == 'delay-trade' || pageId == 'batch-pay') {
                Trade.DialogMgr.initDialogCloseBtn();
            }

			// 积分
			if (pageId == 'my-point') {
				initTradeSearchBox();
			}

			// 退款详情
			if (pageId == 'refund-list') {
				initRefundHelpBox();
			}

            /**
             * 初始化搜索面板
             */
            function initTradeSearchBox() {
                // 折叠展开功能
                Trade.Util.togglePanel('J_SearchBoxToggle', 'J_SearchBox', function(trigger) {
                    if (Dom.hasClass(trigger, 'collapsed-tool-toggle')) {
                        Dom.removeClass(trigger, 'collapsed-tool-toggle');
                    } else {
                        Dom.addClass(trigger, 'collapsed-tool-toggle');
                    }
                });

                // TODO: 日期输入框的初始化
            }

            /**
             * 初始化帮助面板
             */
            function initRefundHelpBox() {
                // 折叠展开功能
                Trade.Util.togglePanel('J_RefundHelpBoxToggle', 'J_RefundHelpBox', function(trigger) {
					var panel = trigger.parentNode;
                    if (Dom.hasClass(panel, 'collapsed')) {
                        Dom.removeClass(panel, 'collapsed');
                    } else {
                        Dom.addClass(panel, 'collapsed');
                    }
                });

                // TODO: 日期输入框的初始化
            }
        }
    };
}());