import SwiftUI
import shared
import WebKit

struct ContentView: View {
    let greet = Greeting().greet()
    var body: some View {
            MyWebView()
//		Text(greet)
//		SimpleWebView(url: URL(string: "https://www.google.com")!)
//      WebViewOpenListener(url: URL(string: "https://www.google.com")!)
//      self.navigationController?.pushViewController(viewController, animated: true)
//      WebViewOpenLoadListener(url: URL(string: "https://www.google.com")!)
//      WebViewOpenLoadListener(url: URL(string: "https://reliable-crocodile.static.domains")!)
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}

struct SimpleWebView: UIViewRepresentable {
    let url: URL

    func makeUIView(context: Context) -> WKWebView  {
        let webView = WKWebView()
        webView.load(URLRequest(url: url))
        return webView
    }

    func updateUIView(_ uiView: WKWebView, context: Context) {
        // Code to update your view
    }
}

struct WebViewOpenListener: UIViewRepresentable {
    let url: URL

    func makeUIView(context: Context) -> WKWebView  {
        let webView = WKWebView()
        webView.navigationDelegate = context.coordinator
        webView.load(URLRequest(url: url))
        return webView
    }

    func updateUIView(_ uiView: WKWebView, context: Context) {
        // Code to update your view
    }

    func makeCoordinator() -> Coordinator {
        Coordinator()
    }

    class Coordinator: NSObject, WKNavigationDelegate {
        func webView(_ webView: WKWebView, didFinish navigation: WKNavigation!) {
            print("Page loaded.")
        }
    }
}

struct WebViewOpenLoadListener: UIViewRepresentable {
    let url: URL


    func makeUIView(context: Context) -> WKWebView  {
    let webView = WKWebView()
        webView.navigationDelegate = context.coordinator
        return webView
    }

    func updateUIView(_ uiView: WKWebView, context: Context) {
        let request = URLRequest(url: self.url)
        uiView.load(request)
    }

    func makeCoordinator() -> Coordinator {
        Coordinator(self)
    }

    class Coordinator: NSObject, WKNavigationDelegate {
        var parent: WebViewOpenLoadListener

        init(_ webView: WebViewOpenLoadListener) {
            self.parent = webView
        }

        func webView(_ webView: WKWebView, didStartProvisionalNavigation navigation: WKNavigation!) {
            print("Start loading the webpage")
        }

        func webView(_ webView: WKWebView, didFinish navigation: WKNavigation!) {
            print("Webpage loading finished")
        }
    }
}

struct MyWebView: UIViewControllerRepresentable {
    typealias UIViewControllerType = ViewController

    func makeUIViewController(context: Context) -> ViewController {
        return ViewController()
    }

    func updateUIViewController(_ uiViewController: ViewController, context: Context) {
        // Update the view controller if needed
    }
}

class ViewController: UIViewController, WKScriptMessageHandler {
    var webView: WKWebView!

    override func loadView() {
        let webConfiguration = WKWebViewConfiguration()
        let userContentController = WKUserContentController()

        userContentController.add(self, name: "toastMe")
        userContentController.add(self, name: "notifyMe")

        webConfiguration.userContentController = userContentController
        webView = WKWebView(frame: .zero, configuration: webConfiguration)
        view = webView
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        if let url = URL(string: "https://reliable-crocodile.static.domains") {
            webView.load(URLRequest(url: url))
        }
    }

    func userContentController(_ userContentController: WKUserContentController, didReceive message: WKScriptMessage) {
        if message.name == "toastMe", let messageBody = message.body as? String {
            print("toastMe: \(messageBody)")
        } else if message.name == "notifyMe", let messageBody = message.body as? String {
            print("notifyMe: \(messageBody)")
        }
    }
}
