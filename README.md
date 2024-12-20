# Android Calculator

一个简洁美观的Android计算器应用，支持基本的数学运算功能。

## 功能特点

- 支持基本运算：加(+)、减(-)、乘(×)、除(÷)
- 支持小数点计算
- 支持括号运算
- 实时计算结果显示
- 错误处理机制
- 清除(C)和全部清除(AC)功能

## 技术特点

- 使用Mozilla的Rhino引擎进行数学表达式求值
- Material Design界面设计
- 适配深色模式
- Edge-to-edge全屏显示支持

## 使用说明

1. **基本运算**
   - 点击数字和运算符进行计算
   - 运算结果会实时显示在上方
   - 按"="查看最终结果

2. **特殊功能**
   - AC：清除所有输入和结果
   - C：删除最后一个输入字符
   - ( )：支持括号运算
   - "."：输入小数点

3. **错误处理**
   - 除以零时显示错误提示
   - 防止输入非法表达式
   - 防止连续输入运算符

## 项目结构
app/
├── src/main/
│ ├── java/
│ │ └── com/bignerdranch/android/calculator/
│ │ └── MainActivity.java
│ └── res/
│ ├── layout/
│ │ └── activity_main.xml
│ └── values/
│ ├── colors.xml
│ ├── strings.xml
│ └── themes.xml


## 依赖项

- androidx.appcompat:appcompat
- com.google.android.material:material
- org.mozilla:rhino - 用于数学表达式求值

## 开发环境要求

- Android Studio Hedgehog 或更高版本
- Android SDK 34
- Gradle 8.2

## 安装说明

1. 克隆项目到本地
2. 使用Android Studio打开项目
3. 等待Gradle同步完成
4. 运行应用到模拟器或实体设备

## 许可证

本项目采用 MIT 许可证。详见 [LICENSE](LICENSE) 文件。

## 贡献指南

欢迎提交问题和改进建议。如果您想贡献代码：

1. Fork 本仓库
2. 创建您的特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交您的更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开一个 Pull Request

## 联系方式

如有任何问题或建议，请通过以下方式联系：

- 项目Issues页面
- Email: your.email@example.com

## 致谢

- [Material Design](https://material.io/design)
- [Mozilla Rhino](https://github.com/mozilla/rhino)
